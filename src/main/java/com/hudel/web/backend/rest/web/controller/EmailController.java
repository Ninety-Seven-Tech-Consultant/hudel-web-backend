package com.hudel.web.backend.rest.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hudel.web.backend.model.constant.ApiPath;
import com.hudel.web.backend.model.entity.Email;
import com.hudel.web.backend.rest.web.model.response.EmailResponse;
import com.hudel.web.backend.rest.web.model.response.rest.RestBaseResponse;
import com.hudel.web.backend.rest.web.model.response.rest.RestListResponse;
import com.hudel.web.backend.rest.web.model.response.rest.RestPageResponse;
import com.hudel.web.backend.rest.web.service.EmailService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Email", description = "Email Service API")
@Validated
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_EMAIL)
public class EmailController extends BaseController {

  @Autowired
  private EmailService emailService;

  @PostMapping(value = ApiPath.SEND_WELCOME_EMAIL)
  public RestBaseResponse sendWelcomeEmail(@PathVariable("email") String email)
      throws JsonProcessingException {
    emailService.sendWelcomeEmail(email);
    return toBaseResponse();
  }

  @GetMapping(value = ApiPath.GET_WHITELISTED_DOMAINS)
  public RestListResponse<String> getWhitelistedDomains() throws JsonProcessingException {
    return toListResponse(emailService.getWhitelistedDomains());
  }

  @PostMapping(value = ApiPath.WHITELIST_DOMAIN)
  public RestListResponse<String> whitelistDomain(@PathVariable("domain") String domain)
      throws JsonProcessingException {
    return toListResponse(emailService.whitelistDomain(domain));
  }

  @DeleteMapping(value = ApiPath.DELETE_WHITELISTED_DOMAIN)
  public RestListResponse<String> deleteWhitelistedDomain(@PathVariable("domain") String domain)
      throws JsonProcessingException {
    return toListResponse(emailService.deleteWhitelistedDomain(domain));
  }

  @PostMapping
  public RestPageResponse<EmailResponse> findByEmail(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size, @RequestParam(required = false) String email) {
    Page<Email> emails = emailService.findByEmail(page, size, email);
    List<EmailResponse> content = emails.getContent().stream()
        .map(this::toEmailResponse)
        .collect(Collectors.toList());
    return toPageResponse(content, emails);
  }

  private EmailResponse toEmailResponse(Email email) {
    EmailResponse response = new EmailResponse();
    BeanUtils.copyProperties(email, response);
    return response;
  }
}
