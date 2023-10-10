package com.hudel.web.backend.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@RefreshScope
@ConfigurationProperties(value = "sysparam")
public class SysparamProperties {

  // JWT Properties
  private String jwtSecret;
  private String jwtCookieName;
  private Integer jwtExpirationTimeInMillis;
}
