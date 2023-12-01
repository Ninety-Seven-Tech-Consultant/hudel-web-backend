package com.hudel.web.backend.rest.web.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpsertNewBlogRequest implements Serializable {

  private static final long serialVersionUID = -278823273992821241L;

  @NotBlank
  private String title;

  @NotNull
  private Integer readDurationInMinutes;

  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date datePublished;

  @NotBlank
  private String content;
}
