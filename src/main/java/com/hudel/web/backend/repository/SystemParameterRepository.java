package com.hudel.web.backend.repository;

import com.hudel.web.backend.model.entity.SystemParameter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SystemParameterRepository extends MongoRepository<SystemParameter, String> {

  Boolean existsByKey(String key);

  SystemParameter findByKey(String key);
}
