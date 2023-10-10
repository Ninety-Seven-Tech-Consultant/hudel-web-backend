package com.hudel.web.backend.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(value = "postmark.feign")
public class PostmarkFeignProperties {

  private String host;
  private int timeoutInMillis;
  private String token;
}
