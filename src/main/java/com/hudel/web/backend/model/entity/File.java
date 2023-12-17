package com.hudel.web.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = File.COLLECTION_NAME)
public class File extends BaseMongoEntity {

  public static final String COLLECTION_NAME = "files";

  private static final long serialVersionUID = 2818784016554378870L;

  private String fileId;
  private String path;
  private String filename;
  private String filetype;
}
