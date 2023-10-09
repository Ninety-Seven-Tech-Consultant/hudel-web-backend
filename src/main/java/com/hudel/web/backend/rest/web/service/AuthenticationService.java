package com.hudel.web.backend.rest.web.service;

import com.hudel.web.backend.model.auth.UserDetailsDto;
import com.hudel.web.backend.rest.web.model.request.LoginRequest;
import com.hudel.web.backend.rest.web.model.request.RegisterRequest;
import org.springframework.http.ResponseCookie;
import reactor.util.function.Tuple2;

public interface AuthenticationService {

  void register(RegisterRequest request);

  Tuple2<UserDetailsDto, ResponseCookie> login(LoginRequest request);
}
