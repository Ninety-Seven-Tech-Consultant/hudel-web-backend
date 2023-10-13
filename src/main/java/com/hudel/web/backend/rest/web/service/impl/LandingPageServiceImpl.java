package com.hudel.web.backend.rest.web.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hudel.web.backend.rest.web.service.EmailService;
import com.hudel.web.backend.rest.web.service.LandingPageService;
import com.hudel.web.backend.rest.web.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LandingPageServiceImpl implements LandingPageService {

  @Autowired
  private StringUtil stringUtil;

  @Autowired
  private EmailService emailService;

  @Override
  public void signUp(String email) throws JsonProcessingException {
    stringUtil.validateEmailNotNullOrBlank(email);
    stringUtil.validateEmailValid(email);
    emailService.sendNewWelcomeEmail(email);
  }
}
