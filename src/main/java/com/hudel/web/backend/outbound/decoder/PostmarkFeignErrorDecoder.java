package com.hudel.web.backend.outbound.decoder;

import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostmarkFeignErrorDecoder implements ErrorDecoder {

  final Decoder decoder;
  final ErrorDecoder errorDecoder = new ErrorDecoder.Default();

  @Override
  public Exception decode(String s, Response response) {
    return errorDecoder.decode(s, response);
  }
}
