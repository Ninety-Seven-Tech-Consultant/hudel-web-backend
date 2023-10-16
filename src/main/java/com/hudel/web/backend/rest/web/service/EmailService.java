package com.hudel.web.backend.rest.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hudel.web.backend.model.entity.Email;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmailService {

  void sendNewWelcomeEmail(String email) throws JsonProcessingException;

  void sendWelcomeEmail(String email);

  List<String> getWhitelistedDomains() throws JsonProcessingException;

  List<String> whitelistDomain(String domain) throws JsonProcessingException;

  List<String> deleteWhitelistedDomain(String domain) throws JsonProcessingException;

  Page<Email> findByEmail(Integer page, Integer size, String email);
}
