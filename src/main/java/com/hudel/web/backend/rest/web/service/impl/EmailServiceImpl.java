package com.hudel.web.backend.rest.web.service.impl;

import com.hudel.web.backend.outbound.PostmarkOutbound;
import com.hudel.web.backend.rest.web.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

  @Autowired
  private PostmarkOutbound postmarkOutbound;

  @Override
  public void sendTest(String email, String name) {
    postmarkOutbound.sendEmailWithTemplate("test-welcome-template",
        Map.of("name", name), "email@hudel.co", email);
  }
}
