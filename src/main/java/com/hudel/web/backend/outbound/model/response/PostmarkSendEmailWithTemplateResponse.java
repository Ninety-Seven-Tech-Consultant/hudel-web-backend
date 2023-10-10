package com.hudel.web.backend.outbound.model.response;

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
public class PostmarkSendEmailWithTemplateResponse implements Serializable {

  private static final long serialVersionUID = 8547903784590181429L;

  private String To;
  private String SubmittedAt;
  private String MessageId;
  private String ErrorCode;
  private String Message;
}
