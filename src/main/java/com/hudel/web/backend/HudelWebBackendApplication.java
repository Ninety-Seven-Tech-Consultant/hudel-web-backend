package com.hudel.web.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class HudelWebBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(HudelWebBackendApplication.class, args);
  }

}
