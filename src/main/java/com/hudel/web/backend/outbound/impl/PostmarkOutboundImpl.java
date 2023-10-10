package com.hudel.web.backend.outbound.impl;

import com.hudel.web.backend.outbound.PostmarkOutbound;
import com.hudel.web.backend.outbound.feign.PostmarkFeign;
import com.hudel.web.backend.outbound.model.request.PostmarkSendEmailWithTemplateRequest;
import com.hudel.web.backend.outbound.model.response.PostmarkSendEmailWithTemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PostmarkOutboundImpl implements PostmarkOutbound {

  @Autowired
  private PostmarkFeign postmarkFeign;

  @Override
  public PostmarkSendEmailWithTemplateResponse sendEmailWithTemplate(String templateAlias,
      Map<String, Object> templateModel, String from, String to) {
    return postmarkFeign.sendEmailWithTemplate(buildSendEmailWithTemplateRequest(templateAlias,
        templateModel, from, to));
  }

  private PostmarkSendEmailWithTemplateRequest buildSendEmailWithTemplateRequest(
      String templateAlias, Map<String, Object> templateModel, String from, String to) {
    return PostmarkSendEmailWithTemplateRequest.builder()
        .TemplateAlias(templateAlias)
        .TemplateModel(templateModel)
        .From(from)
        .To(to)
        .build();
  }
}
