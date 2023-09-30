package com.hudel.web.backend.repository;

import com.hudel.web.backend.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

  User findByUsername(String username);
}
