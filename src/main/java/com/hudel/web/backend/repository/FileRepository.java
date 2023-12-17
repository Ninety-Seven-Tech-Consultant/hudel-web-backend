package com.hudel.web.backend.repository;

import com.hudel.web.backend.model.entity.File;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FileRepository extends MongoRepository<File, String> {

  Optional<File> findByFileId(String fileId);
}
