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
@Document(collection = Email.COLLECTION_NAME)
public class Email extends BaseMongoEntity {

  public static final String COLLECTION_NAME = "emails";

  private static final long serialVersionUID = -2273279183288929602L;

  private String email;
  private boolean isEligible;
  private boolean isWelcomeMessageSent;

  public Email(String id, Date createdAt, Date updatedAt, String email,
      boolean isEligible, boolean isWelcomeMessageSent) {
    super(id, createdAt, updatedAt);
    this.email = email;
    this.isEligible = isEligible;
    this.isWelcomeMessageSent = isWelcomeMessageSent;
  }
}
