package com.hudel.web.backend.rest.web.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.hudel.web.backend.model.constant.ErrorCode;
import com.hudel.web.backend.model.constant.SystemParameterKey;
import com.hudel.web.backend.model.entity.Email;
import com.hudel.web.backend.model.entity.SystemParameter;
import com.hudel.web.backend.model.exception.BaseException;
import com.hudel.web.backend.repository.EmailRepository;
import com.hudel.web.backend.rest.web.service.EmailService;
import com.hudel.web.backend.rest.web.service.LandingPageService;
import com.hudel.web.backend.rest.web.service.SystemParameterService;
import com.hudel.web.backend.rest.web.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LandingPageServiceImpl implements LandingPageService {

  @Autowired
  private StringUtil stringUtil;

  @Autowired
  private EmailRepository emailRepository;

  @Autowired
  private SystemParameterService systemParameterService;

  @Autowired
  private EmailService emailService;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void signUp(String email) throws JsonProcessingException {
    validateEmailNotNullOrBlank(email);
    stringUtil.validateEmailValid(email);
    sendWelcomeEmail(emailRepository.save(buildEmail(email)));
  }

  private void validateEmailNotNullOrBlank(String email) {
    if (stringUtil.isStringNullOrBlank(email)) {
      throw new BaseException(ErrorCode.EMAIL_BLANK_OR_NULL);
    }
  }

  private Email buildEmail(String email) throws JsonProcessingException {
    return Email.builder()
        .email(email)
        .isWelcomeMessageSent(isEmailWhitelisted(email))
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

  private void sendWelcomeEmail(Email email) {
    if (!email.isWelcomeMessageSent()) {
      throw new BaseException(ErrorCode.EMAIL_NOT_ELIGIBLE);
    }
    emailService.sendWelcomeEmail(email.getEmail());
  }
}
