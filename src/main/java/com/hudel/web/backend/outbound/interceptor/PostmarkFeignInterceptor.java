package com.hudel.web.backend.outbound.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
public class PostmarkFeignInterceptor implements RequestInterceptor {

  private String token;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    requestTemplate.headers(Map.of(
        "Content-Type", Collections.singletonList("application/json"),
        "Accept", Collections.singletonList("application/json"),
        "X-Postmark-Server-Token", Collections.singletonList(token)));
  }
}
