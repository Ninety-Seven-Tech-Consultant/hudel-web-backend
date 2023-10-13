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
@Document(collection = SystemParameter.COLLECTION_NAME)
public class SystemParameter extends BaseMongoEntity {

  public static final String COLLECTION_NAME = "system_parameters";

  private static final long serialVersionUID = 5868998138685941606L;

  private String key;
  private Object data;
  private String type;

  public SystemParameter(String id, Date createdAt, Date updatedAt, String key, Object data,
      String type) {
    super(id, createdAt, updatedAt);
    this.key = key;
    this.data = data;
    this.type = type;
  }
}
