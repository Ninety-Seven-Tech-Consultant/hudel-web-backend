package com.hudel.web.backend.rest.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface EmailService {

  void sendNewWelcomeEmail(String email) throws JsonProcessingException;

  void sendWelcomeEmail(String email);

  List<String> getWhitelistedDomains() throws JsonProcessingException;

  List<String> whitelistDomain(String domain) throws JsonProcessingException;
}
