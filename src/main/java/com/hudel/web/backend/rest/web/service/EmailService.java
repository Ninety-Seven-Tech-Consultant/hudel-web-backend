package com.hudel.web.backend.rest.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface EmailService {

  void sendNewWelcomeEmail(String email) throws JsonProcessingException;

  void sendWelcomeEmail(String email);
}
