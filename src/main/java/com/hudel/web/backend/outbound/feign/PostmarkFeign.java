package com.hudel.web.backend.outbound.feign;

import com.hudel.web.backend.outbound.model.request.PostmarkSendEmailWithTemplateRequest;
import com.hudel.web.backend.outbound.model.response.PostmarkSendEmailWithTemplateResponse;
import feign.RequestLine;

public interface PostmarkFeign {

  @RequestLine("POST /email/withTemplate/")
  PostmarkSendEmailWithTemplateResponse sendEmailWithTemplate(
      PostmarkSendEmailWithTemplateRequest request);
}
