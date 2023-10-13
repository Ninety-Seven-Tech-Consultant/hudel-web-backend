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
import com.hudel.web.backend.rest.web.service.EmailService;
import com.hudel.web.backend.rest.web.service.SystemParameterService;
import com.hudel.web.backend.rest.web.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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
    Email emailEntity = buildDefaultEmail(email);
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

  private Email buildDefaultEmail(String email) throws JsonProcessingException {
    return Email.builder()
        .email(email)
        .isEligible(isEmailWhitelisted(email))
        .isWelcomeMessageSent(false)
        .build();
  }

  private boolean isEmailWhitelisted(String email) throws JsonProcessingException {
    SystemParameter sysParam =
        systemParameterService.findByKey(SystemParameterKey.EMAIL_DOMAIN_WHITELIST);
    List<String> whitelistedDomains = objectMapper.readValue(new Gson().toJson(sysParam.getData()),
        new TypeReference<List<String>>(){});
    for (String domain : whitelistedDomains) {
      if (email.endsWith(domain)) {
        return true;
      }
    }
    return false;
  }

  private void sendAndSaveWelcomeEmail(Email email) {
    if (email.isEligible()) {
      postmarkOutbound.sendEmailWithTemplate("welcome", Collections.emptyMap(),
          sysparamProperties.getEmailDefaultFrom(), email.getEmail());
      email.setWelcomeMessageSent(true);
      emailRepository.save(email);
      return;
    }
    emailRepository.save(email);
    throw new BaseException(ErrorCode.EMAIL_NOT_ELIGIBLE);
  }
}
