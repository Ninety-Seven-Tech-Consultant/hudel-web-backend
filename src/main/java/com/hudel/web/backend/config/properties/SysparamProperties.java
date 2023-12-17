package com.hudel.web.backend.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@RefreshScope
@ConfigurationProperties(value = "sysparam")
public class SysparamProperties {

  @PostConstruct
  public void init() {
    maxImageCompressionSizeInKbValue = Float.parseFloat(imageMaxCompressionSizeInKb);
  }

  // JWT Properties
  private String jwtSecret;
  private String jwtCookieName;
  private Integer jwtExpirationTimeInMillis;

  // Email
  private String emailDefaultFrom;

  // Image
  private String imageRetrieveUrl;
  private String imageMaxCompressionSizeInKb;

  // Blog
  private String blogDefaultImageId;

  private Float maxImageCompressionSizeInKbValue;
}
