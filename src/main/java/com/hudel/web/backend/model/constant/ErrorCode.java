package com.hudel.web.backend.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  USER_EMAIL_INVALID("ERR-PA40001", 400,
      "Email requested is invalid, please re-check"),
  PASSWORD_LENGTH_INVALID("ERR-PA40002", 400,
      "Password must be 8 - 16 characters in length"),
  PASSWORD_UPPERCASE_COUNT_INVALID("ERR-PA40003", 400,
      "Password must have one or more uppercase character"),
  PASSWORD_LOWERCASE_COUNT_INVALID("ERR-PA40004", 400,
      "Password must have one or more lowercase character"),
  PASSWORD_DIGIT_COUNT_INVALID("ERR-PA40005", 400,
      "Password must have one or more numerical character"),

  EMAIL_ALREADY_EXISTS("ERR-PA42201", 422,
      "The requested email already exists"),
  USERNAME_ALREADY_EXISTS("ERR-PA42202", 422,
      "The requested username already exists"),

  UNSPECIFIED_ERROR("ERR-PA50001", 500,
      "Unspecified error that is not handled by generid handler");

  private final String errorCode;
  private final int httpStatus;
  private final String description;
}