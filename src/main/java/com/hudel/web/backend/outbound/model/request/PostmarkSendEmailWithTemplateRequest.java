package com.hudel.web.backend.outbound.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostmarkSendEmailWithTemplateRequest implements Serializable {

  private static final long serialVersionUID = 8020176735930226926L;

  private String TemplateAlias;
  private Map<String, Object> TemplateModel;
  private String From;
  private String To;
}
