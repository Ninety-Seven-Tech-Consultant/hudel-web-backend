package com.hudel.web.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = Blog.COLLECTION_NAME)
public class Blog extends BaseMongoEntity {

  public static final String COLLECTION_NAME = "blogs";

  private static final long serialVersionUID = 1702828689525316309L;

  private String title;
  private Integer readDurationInMinutes;
  private Date datePublished;
  private String content;
  private String shortDescription;
  private BlogImage coverImage;
  private BlogImage contentImage;
}
