package com.hudel.web.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hudel.web.backend.config.handler.CustomDeserializationProblemHandler;
import com.hudel.web.backend.outbound.decoder.PostmarkFeignErrorDecoder;
import com.hudel.web.backend.outbound.interceptor.PostmarkFeignInterceptor;
import com.hudel.web.backend.config.properties.PostmarkFeignProperties;
import com.hudel.web.backend.outbound.feign.PostmarkFeign;
import feign.Feign;
import feign.Request;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

  @Autowired
  private PostmarkFeignProperties postmarkFeignProperties;

  @Bean
  public static CustomDeserializationProblemHandler customDeserializationProblemHandler() {
    return new CustomDeserializationProblemHandler();
  }

  @Bean
  public static ObjectMapper objectMapper() {
    return new ObjectMapper().addHandler(FeignConfig.customDeserializationProblemHandler());
  }

  @Bean
  public PostmarkFeign postmarkFeign() {
    return Feign.builder()
        .requestInterceptor(new PostmarkFeignInterceptor(postmarkFeignProperties.getToken()))
        .encoder(new FormEncoder(new JacksonEncoder(objectMapper())))
        .decoder(new JacksonDecoder(objectMapper()))
        .logger(new Slf4jLogger(PostmarkFeign.class))
        .errorDecoder(new PostmarkFeignErrorDecoder(new JacksonDecoder(objectMapper())))
        .options(new Request.Options(postmarkFeignProperties.getTimeoutInMillis(),
            postmarkFeignProperties.getTimeoutInMillis()))
        .target(PostmarkFeign.class, postmarkFeignProperties.getHost());
  }
}
