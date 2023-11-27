package com.hudel.web.backend.repository;

import com.hudel.web.backend.model.entity.Blog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogRepository extends MongoRepository<Blog, String> {
}
