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
@Document(collection = User.COLLECTION_NAME)
public class User extends BaseMongoEntity {

  public static final String COLLECTION_NAME = "users";

  private static final long serialVersionUID = -8519342404507639572L;

  private String username;
  private String password;

  public User(String id, Date createdAt, Date updatedAt, String username, String password) {
    super(id, createdAt, updatedAt);
    this.username = username;
    this.password = password;
  }
}
