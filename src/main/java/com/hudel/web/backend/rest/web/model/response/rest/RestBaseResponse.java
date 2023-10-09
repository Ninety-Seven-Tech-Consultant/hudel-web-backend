package com.hudel.web.backend.rest.web.model.response.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hudel.web.backend.rest.web.model.response.error.ErrorFieldAndMessageResponse;
import com.hudel.web.backend.rest.web.model.response.error.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Data
@SuperBuilder(builderMethodName = "parentBuilder")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestBaseResponse implements Serializable {

  private static final long serialVersionUID = -4663652365668378120L;

  private String errorMessage;
  private String errorCode;
  private boolean success;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<ErrorResponse> errorList;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<ErrorFieldAndMessageResponse> errorFieldList;
}
