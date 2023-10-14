package com.hudel.web.backend.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  EMAIL_FORMAT_INVALID("ERR-PA40001", 400,
      "Email requested is invalid, please re-check"),
  PASSWORD_LENGTH_INVALID("ERR-PA40002", 400,
      "Password must be 8 - 16 characters in length"),
  PASSWORD_UPPERCASE_COUNT_INVALID("ERR-PA40003", 400,
      "Password must have one or more uppercase character"),
  PASSWORD_LOWERCASE_COUNT_INVALID("ERR-PA40004", 400,
      "Password must have one or more lowercase character"),
  PASSWORD_DIGIT_COUNT_INVALID("ERR-PA40005", 400,
      "Password must have one or more numerical character"),
  EMAIL_BLANK_OR_NULL("ERR-PA40006", 400,
      "Email requested must not be blank or null"),
  SYSTEM_PARAMETER_TYPE_INVALID("ERR-PA40007", 400,
      "The only allowed system parameter type are string & integer"),
  SYSTEM_PARAMETER_DATA_INVALID("ERR-PA40008", 400,
      "The requested data type does not matched the request given"),
  SYSTEM_PARAMETER_TITLE_ALREADY_EXISTS("ERR-PA40009", 400,
      "System parameter with the requested title already exists"),
  SYSTEM_PARAMETER_NOT_FOUND("ERR-PA40010", 400,
      "The requested system parameter does not exists"),
  EMAIL_NOT_ELIGIBLE("ERR-PA40011", 400,
      "Oops, looks like Hudel is not available for your university yet!"),
  EMAIL_NOT_FOUND("ERR-PA40012", 400,
      "The requested email does not exists"),
  DOMAIN_BLANK_OR_NULL("ERR-PA40013", 400,
      "Domain requested must not be blank or null"),
  WHITELISTED_DOMAIN_ALREADY_EXISTS("ERR-PA40014", 400,
      "The domain requested has already been whitelisted"),

  USER_CREDENTIALS_INVALID("ERR-PA40101", 401,
      "The requested username and password is invalid"),

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
