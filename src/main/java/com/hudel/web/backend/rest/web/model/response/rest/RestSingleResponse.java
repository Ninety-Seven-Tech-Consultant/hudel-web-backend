package com.hudel.web.backend.rest.web.model.response.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestSingleResponse<T> extends RestBaseResponse {

  private static final long serialVersionUID = -4522210908807120068L;

  private T content;

  public RestSingleResponse(RestBaseResponse baseResponse) {
    setErrorCode(baseResponse.getErrorCode());
    setErrorMessage(baseResponse.getErrorMessage());
    setSuccess(baseResponse.isSuccess());
  }
}
