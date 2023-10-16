package com.hudel.web.backend.outbound;

import com.hudel.web.backend.outbound.model.response.PostmarkSendEmailWithTemplateResponse;

import java.util.Map;

public interface PostmarkOutbound {

  PostmarkSendEmailWithTemplateResponse sendEmailWithTemplate(String templateAlias,
      Map<String, Object> templateModel, String from, String to);
}
