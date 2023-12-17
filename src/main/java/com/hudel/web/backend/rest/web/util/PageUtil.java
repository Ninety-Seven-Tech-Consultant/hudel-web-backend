package com.hudel.web.backend.rest.web.util;

import com.hudel.web.backend.model.constant.ErrorCode;
import com.hudel.web.backend.model.exception.BaseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PageUtil {

  public static PageRequest validateAndGetPageRequest(Integer page, Integer size) {
    page = validateAndInitializePageNumber(page);
    size = validateAndInitializePageSize(size);
    return PageRequest.of(page, size);
  }

  private static int validateAndInitializePageNumber(Integer page) {
    if (Objects.nonNull(page) && page < 0) {
      throw new BaseException(ErrorCode.PAGE_NUMBER_LESS_THAN_ZERO);
    }
    return Objects.isNull(page) ? 0 : page;
  }

  private static int validateAndInitializePageSize(Integer size) {
    if (Objects.nonNull(size) && size <= 0) {
      throw new BaseException(ErrorCode.PAGE_SIZE_LESS_THAN_OR_EQUAL_TO_ZERO);
    }
    return Objects.isNull(size) ? 10 : size;
  }
}
