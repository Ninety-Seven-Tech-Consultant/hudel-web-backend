package com.hudel.web.backend.rest.web.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.hudel.web.backend.config.properties.SysparamProperties;
import com.hudel.web.backend.model.constant.ErrorCode;
import com.hudel.web.backend.model.constant.SystemParameterKey;
import com.hudel.web.backend.model.entity.Email;
import com.hudel.web.backend.model.entity.SystemParameter;
import com.hudel.web.backend.model.exception.BaseException;
import com.hudel.web.backend.outbound.PostmarkOutbound;
import com.hudel.web.backend.repository.EmailRepository;
import com.hudel.web.backend.rest.web.model.request.UpsertSystemParameterRequest;
import com.hudel.web.backend.rest.web.service.EmailService;
import com.hudel.web.backend.rest.web.service.SystemParameterService;
import com.hudel.web.backend.rest.web.util.StringUtil;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {

  @Autowired
  private StringUtil stringUtil;

  @Autowired
  private EmailRepository emailRepository;

  @Autowired
  private PostmarkOutbound postmarkOutbound;

  @Autowired
  private SysparamProperties sysparamProperties;

  @Autowired
  private SystemParameterService systemParameterService;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void sendNewWelcomeEmail(String email) throws JsonProcessingException {
    Email emailEntity = getOrDefault(email);
    sendAndSaveWelcomeEmail(emailEntity);
  }

  @Override
  public void sendWelcomeEmail(String email) {
    stringUtil.validateEmailNotNullOrBlank(email);
    Email emailEntity = emailRepository.findByEmail(email);
    if (Objects.isNull(emailEntity)) {
      throw new BaseException(ErrorCode.EMAIL_NOT_FOUND);
    }
    sendAndSaveWelcomeEmail(emailEntity);
  }

  @Override
  public List<String> getWhitelistedDomains() throws JsonProcessingException {
    SystemParameter sysParam =
        systemParameterService.findByKey(SystemParameterKey.EMAIL_DOMAIN_WHITELIST);
    return objectMapper.readValue(new Gson().toJson(sysParam.getData()),
        new TypeReference<List<String>>(){});
  }

  @Override
  public List<String> whitelistDomain(String domain) throws JsonProcessingException {
    stringUtil.validatedWhitelistDomainNotNullOrBlank(domain);
    SystemParameter sysParam =
        systemParameterService.findByKey(SystemParameterKey.EMAIL_DOMAIN_WHITELIST);
    List<String> whitelistedDomains = objectMapper.readValue(new Gson().toJson(sysParam.getData()),
        new TypeReference<List<String>>(){});
    if (whitelistedDomains.contains(domain)) {
      throw new BaseException(ErrorCode.WHITELISTED_DOMAIN_ALREADY_EXISTS);
    }
    whitelistedDomains.add(domain);
    sysParam.setData(whitelistedDomains);
    systemParameterService.update(buildUpsertSystemParameterRequest(sysParam));
    updateIsEligibleStatusByDomain(domain, true);
    return whitelistedDomains;
  }

  @Override
  public List<String> deleteWhitelistedDomain(String domain) throws JsonProcessingException {
    stringUtil.validatedWhitelistDomainNotNullOrBlank(domain);
    SystemParameter sysParam =
        systemParameterService.findByKey(SystemParameterKey.EMAIL_DOMAIN_WHITELIST);
    List<String> whitelistedDomains = objectMapper.readValue(new Gson().toJson(sysParam.getData()),
        new TypeReference<List<String>>(){});
    whitelistedDomains.removeIf(string -> string.equals(domain));
    sysParam.setData(whitelistedDomains);
    systemParameterService.update(buildUpsertSystemParameterRequest(sysParam));
    updateIsEligibleStatusByDomain(domain, false);
    return whitelistedDomains;
  }

  @Override
  public Page<Email> findByEmail(Integer page, Integer size, String email) {
    PageRequest pageRequest = validateAndGetPageRequest(page, size);
    return emailRepository.searchByEmail(Objects.isNull(email) ? "" : email, pageRequest);
  }

  private Email getOrDefault(String email) throws JsonProcessingException {
    Email emailEntity = emailRepository.findByEmail(email);
    if (Objects.isNull(emailEntity)) {
      return Email.builder()
          .email(email)
          .isEligible(isEmailWhitelisted(email))
          .isWelcomeMessageSent(false)
          .build();
    }
    return emailEntity;
  }

  private boolean isEmailWhitelisted(String email) throws JsonProcessingException {
    List<String> whitelistedDomains = getWhitelistedDomains();
    for (String domain : whitelistedDomains) {
      if (email.endsWith(domain)) {
        return true;
      }
    }
    return false;
  }

  private void sendAndSaveWelcomeEmail(Email email) {
    if (email.isEligible()) {
      Map<String, Object> templateModel = Map.of("randomText", RandomString.make(64));
      postmarkOutbound.sendEmailWithTemplate("welcome", templateModel,
          sysparamProperties.getEmailDefaultFrom(), email.getEmail());
      email.setWelcomeMessageSent(true);
      emailRepository.save(email);
      return;
    }
    emailRepository.save(email);
    throw new BaseException(ErrorCode.EMAIL_NOT_ELIGIBLE);
  }

  private UpsertSystemParameterRequest buildUpsertSystemParameterRequest(
      SystemParameter systemParameter) {
    return UpsertSystemParameterRequest.builder()
        .key(systemParameter.getKey())
        .data(systemParameter.getData())
        .type(systemParameter.getType())
        .build();
  }

  private void updateIsEligibleStatusByDomain(String domain, boolean isEligible) {
    List<Email> emailsToBeUpdated = emailRepository.findAllByDomainEndsWith(domain);
    emailsToBeUpdated.forEach(email -> {
      email.setEligible(isEligible);
    });
    emailRepository.saveAll(emailsToBeUpdated);
  }

  private PageRequest validateAndGetPageRequest(Integer page, Integer size) {
    page = validateAndInitializePageNumber(page);
    size = validateAndInitializePageSize(size);
    return PageRequest.of(page, size);
  }

  private int validateAndInitializePageNumber(Integer page) {
    if (Objects.nonNull(page) && page < 0) {
      throw new BaseException(ErrorCode.PAGE_NUMBER_LESS_THAN_ZERO);
    }
    return Objects.isNull(page) ? 0 : page;
  }

  private int validateAndInitializePageSize(Integer size) {
    if (Objects.nonNull(size) && size <= 0) {
      throw new BaseException(ErrorCode.PAGE_SIZE_LESS_THAN_OR_EQUAL_TO_ZERO);
    }
    return Objects.isNull(size) ? 10 : size;
  }
}
