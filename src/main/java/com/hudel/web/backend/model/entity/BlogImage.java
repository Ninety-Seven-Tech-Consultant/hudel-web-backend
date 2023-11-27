package com.hudel.web.backend.model.entity;

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
public class BlogImage implements Serializable {

  private static final long serialVersionUID = 2616277104317873771L;

  private String imageId;
  private String url;
  private boolean isDefault;
}
