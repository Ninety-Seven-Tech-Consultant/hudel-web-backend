package com.hudel.web.backend.rest.web.service;

import com.hudel.web.backend.rest.web.model.request.RegisterRequest;

public interface AuthenticationService {

  void register(RegisterRequest request);
}
