package com.hudel.web.backend.rest.web.model.response.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestListResponse<T> extends RestBaseResponse {

  private static final long serialVersionUID = 5019313038136229574L;

  private List<T> content;

  public RestListResponse(RestBaseResponse baseResponse) {
    setErrorCode(baseResponse.getErrorCode());
    setErrorMessage(baseResponse.getErrorMessage());
    setSuccess(baseResponse.isSuccess());
  }
}
