package com.hudel.web.backend.rest.web.service.impl;

import com.hudel.web.backend.model.constant.ErrorCode;
import com.hudel.web.backend.model.entity.Email;
import com.hudel.web.backend.model.exception.BaseException;
import com.hudel.web.backend.repository.EmailRepository;
import com.hudel.web.backend.rest.web.service.LandingPageService;
import com.hudel.web.backend.rest.web.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LandingPageServiceImpl implements LandingPageService {

  @Autowired
  private StringUtil stringUtil;

  @Autowired
  private EmailRepository emailRepository;

  @Override
  public void signUp(String email) {
    validateEmailNotNullOrBlank(email);
    stringUtil.validateEmailValid(email);
    emailRepository.save(buildEmail(email));
  }

  private void validateEmailNotNullOrBlank(String email) {
    if (stringUtil.isStringNullOrBlank(email)) {
      throw new BaseException(ErrorCode.EMAIL_BLANK_OR_NULL);
    }
  }

  private Email buildEmail(String email) {
    return Email.builder()
        .email(email)
        .isWelcomeMessageSent(false)
        .build();
  }
}
