package com.hudel.web.backend.rest.web.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemParameterResponse implements Serializable {

  private static final long serialVersionUID = -8869789255913177806L;

  private String key;
  private Object data;
  private String type;
}
