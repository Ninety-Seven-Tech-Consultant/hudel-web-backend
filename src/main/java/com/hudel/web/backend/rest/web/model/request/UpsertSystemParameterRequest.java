package com.hudel.web.backend.rest.web.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpsertSystemParameterRequest implements Serializable {

  private static final long serialVersionUID = 5639440123737484159L;

  @NotBlank
  private String key;

  @NotNull
  private Object data;

  @NotBlank
  private String type;
}
