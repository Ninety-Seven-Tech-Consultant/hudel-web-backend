package com.hudel.web.backend.rest.web.controller;

import com.hudel.web.backend.model.auth.UserDetailsDto;
import com.hudel.web.backend.model.constant.ApiPath;
import com.hudel.web.backend.rest.web.model.request.LoginRequest;
import com.hudel.web.backend.rest.web.model.request.RegisterRequest;
import com.hudel.web.backend.rest.web.model.response.LoginResponse;
import com.hudel.web.backend.rest.web.model.response.rest.RestBaseResponse;
import com.hudel.web.backend.rest.web.model.response.rest.RestSingleResponse;
import com.hudel.web.backend.rest.web.service.AuthenticationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.util.function.Tuple2;

import javax.validation.Valid;

@Api(value = "Authentication", description = "Authentication Service API")
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_AUTHENTICATION)
public class AuthenticationController extends BaseController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping(value = ApiPath.REGISTER)
  public RestBaseResponse register(@Valid @RequestBody RegisterRequest request) {
    authenticationService.register(request);
    return toBaseResponse();
  }

  @PostMapping(value = ApiPath.LOGIN)
  public ResponseEntity<RestSingleResponse<LoginResponse>> login(
      @Valid @RequestBody LoginRequest request) {
    Tuple2<UserDetailsDto, ResponseCookie> tuple = authenticationService.login(request);
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, tuple.getT2().toString())
        .body(toSingleResponse(toLoginResponse(tuple)));
  }

  private LoginResponse toLoginResponse(Tuple2<UserDetailsDto, ResponseCookie> tuple) {
    return LoginResponse.builder()
        .email(tuple.getT1().getEmail())
        .username(tuple.getT1().getUsername())
        .token(tuple.getT2().getValue())
        .build();
  }
}
