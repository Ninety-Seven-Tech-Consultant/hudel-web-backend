package com.hudel.web.backend.rest.web.service.impl;

import com.hudel.web.backend.model.constant.ErrorCode;
import com.hudel.web.backend.model.entity.User;
import com.hudel.web.backend.model.exception.BaseException;
import com.hudel.web.backend.repository.UserRepository;
import com.hudel.web.backend.rest.web.model.request.RegisterRequest;
import com.hudel.web.backend.rest.web.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder encoder;

  private final int PASSWORD_MIN_LENGTH = 8;
  private final int PASSWORD_MAX_LENGTH = 16;

  @Override
  public void register(RegisterRequest request) {
    validateRegisterRequest(request);
    userRepository.save(buildUser(request));
  }

  private void validateRegisterRequest(RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new BaseException(ErrorCode.EMAIL_ALREADY_EXISTS);
    } else if (userRepository.existsByUsername(request.getUsername())) {
      throw new BaseException(ErrorCode.USERNAME_ALREADY_EXISTS);
    }
    validateEmailValid(request.getEmail());
    validatePasswordValid(request.getPassword());
  }


  private void validateEmailValid(String email) {
    Pattern pattern = Pattern.compile("^(.+)@(\\S+)$");
    if (!pattern.matcher(email).matches()) {
      throw new BaseException(ErrorCode.USER_EMAIL_INVALID);
    }
  }

  private void validatePasswordValid(String password) {
    if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
      throw new BaseException(ErrorCode.PASSWORD_LENGTH_INVALID);
    }

    int upCount = 0; int lowCount = 0; int digit = 0;
    for (int i = 0; i < password.length(); i++) {
      char c = password.charAt(i);
      if (Character.isUpperCase(c)) {
        upCount++;
      } else if (Character.isLowerCase(c)) {
        lowCount++;
      } else if (Character.isDigit(c)) {
        digit++;
      }
    }

    if (upCount == 0) {
      throw new BaseException(ErrorCode.PASSWORD_UPPERCASE_COUNT_INVALID);
    } else if (lowCount == 0) {
      throw new BaseException(ErrorCode.PASSWORD_LOWERCASE_COUNT_INVALID);
    } else if (digit == 0) {
      throw new BaseException(ErrorCode.PASSWORD_DIGIT_COUNT_INVALID);
    }
  }

  private User buildUser(RegisterRequest request) {
    return User.builder()
        .email(request.getEmail())
        .username(request.getUsername())
        .password(encoder.encode(request.getPassword()))
        .build();
  }
}
