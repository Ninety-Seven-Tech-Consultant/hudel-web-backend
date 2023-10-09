package com.hudel.web.backend.model.dto;

import com.hudel.web.backend.model.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {

  private String errorCode;
  private String errorMessage;
  private ErrorCode translatedErrorCode;
  private boolean isUseTranslatedError;
}
