package com.hudel.web.backend.rest.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface LandingPageService {

  void signUp(String email) throws JsonProcessingException;
}
