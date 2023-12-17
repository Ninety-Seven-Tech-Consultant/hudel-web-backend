package com.hudel.web.backend.rest.web.service;

import com.hudel.web.backend.model.entity.File;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.function.Tuple2;

import java.io.IOException;

public interface ImageService {

  File uploadImage(MultipartFile file) throws IOException;

  Tuple2<String, byte[]> retrieveImageById(String id) throws IOException;

  void deleteImageById(String id);
}
