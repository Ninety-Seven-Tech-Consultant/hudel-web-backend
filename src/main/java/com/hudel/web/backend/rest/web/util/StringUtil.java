package com.hudel.web.backend.rest.web.util;

import com.hudel.web.backend.model.constant.ErrorCode;
import com.hudel.web.backend.model.exception.BaseException;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Pattern;

@Component
public class StringUtil {

  private final int PASSWORD_MIN_LENGTH = 8;
  private final int PASSWORD_MAX_LENGTH = 16;

  public boolean isStringNullOrBlank(String string) {
    return Objects.isNull(string) || StringUtils.isBlank(string);
  }

  public void validateEmailValid(String email) {
    Pattern pattern = Pattern.compile("^(.+)@(\\S+)$");
    if (!pattern.matcher(email).matches()) {
      throw new BaseException(ErrorCode.EMAIL_FORMAT_INVALID);
    }
  }

  public void validatePasswordValid(String password) {
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

  public void validateEmailNotNullOrBlank(String email) {
    if (isStringNullOrBlank(email)) {
      throw new BaseException(ErrorCode.EMAIL_BLANK_OR_NULL);
    }
  }

  public void validatedWhitelistDomainNotNullOrBlank(String domain) {
    if (isStringNullOrBlank(domain)) {
      throw new BaseException(ErrorCode.DOMAIN_BLANK_OR_NULL);
    }
  }

  public String generateFileId() {
    return generateIdOfPrefixAndLength("FIL-", 6);
  }

  private String generateIdOfPrefixAndLength(String prefix, int length) {
    return prefix + RandomString.make(length).toUpperCase();
  }
}
