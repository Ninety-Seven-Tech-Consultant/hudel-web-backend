package com.hudel.web.backend.rest.web.controller;

import com.hudel.web.backend.model.constant.ApiPath;
import com.hudel.web.backend.rest.web.model.response.rest.RestBaseResponse;
import com.hudel.web.backend.rest.web.service.EmailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Email", description = "Email Service API")
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_EMAIL)
public class EmailController extends BaseController {

  @Autowired
  private EmailService emailService;

  @PostMapping(value = ApiPath.EMAIL_SEND_TEST)
  public RestBaseResponse sendTest(@RequestParam String email, @RequestParam String name) {
    emailService.sendTest(email, name);
    return toBaseResponse();
  }
}
