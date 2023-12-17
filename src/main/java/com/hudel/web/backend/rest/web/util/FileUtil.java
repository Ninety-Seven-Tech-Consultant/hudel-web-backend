package com.hudel.web.backend.rest.web.util;

import com.hudel.web.backend.model.constant.ErrorCode;
import com.hudel.web.backend.model.exception.BaseException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {

  public static void validateFileNotEmpty(MultipartFile file) {
    if (file.isEmpty()) {
      throw new BaseException(ErrorCode.FILE_IS_EMPTY);
    }
  }
}
