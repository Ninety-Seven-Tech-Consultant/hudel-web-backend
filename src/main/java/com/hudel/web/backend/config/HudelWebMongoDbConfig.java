package com.hudel.web.backend.config;

import com.hudel.web.backend.config.properties.MongoDbProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
    basePackages = "com.hudel.web.backend.repository",
    mongoTemplateRef = "HudelWebMongoTemplate")
@ConfigurationProperties(prefix = "hudel-web")
public class HudelWebMongoDbConfig extends MongoDbConfig {

  public HudelWebMongoDbConfig(MongoDbProperties mongoDbProperties) {
    super(mongoDbProperties);
  }

  @Override
  @Bean(name = "HudelWebMongoTemplate")
  public MongoTemplate mongoTemplate() throws Exception {
    return new MongoTemplate(mongoClient(), mongoProperties.getDatabase());
  }
}
