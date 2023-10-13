package com.hudel.web.backend.rest.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hudel.web.backend.model.constant.ApiPath;
import com.hudel.web.backend.rest.web.model.response.rest.RestBaseResponse;
import com.hudel.web.backend.rest.web.service.LandingPageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Landing-Page", description = "Landing-Page Service API")
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_LANDING_PAGE)
public class LandingPageController extends BaseController {

  @Autowired
  private LandingPageService landingPageService;

  @PostMapping(value = ApiPath.LANDING_PAGE_SIGN_UP)
  public RestBaseResponse signUp(@PathVariable("email") String email)
      throws JsonProcessingException {
    landingPageService.signUp(email);
    return toBaseResponse();
  }
}
