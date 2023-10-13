package com.hudel.web.backend.repository;

import com.hudel.web.backend.model.entity.Email;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailRepository extends MongoRepository<Email, String> {

  Email findByEmail(String email);
}
