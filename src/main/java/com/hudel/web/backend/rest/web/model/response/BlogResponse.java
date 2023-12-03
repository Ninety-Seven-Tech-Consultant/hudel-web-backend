package com.hudel.web.backend.rest.web.model.response;

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
public class BlogResponse implements Serializable {

  private static final long serialVersionUID = -2732676811408347967L;

  private String id;
  private String title;
  private Integer readDurationInMinutes;
  private String datePublished;
  private String content;
  private String shortDescription;
  private String coverImageUrl;
  private String contentImageUrl;
}
