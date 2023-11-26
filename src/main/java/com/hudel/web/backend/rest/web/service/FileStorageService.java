package com.hudel.web.backend.rest.web.service;

import org.springframework.web.multipart.MultipartFile;
import reactor.util.function.Tuple3;

import java.io.IOException;

public interface FileStorageService {

  Tuple3<String, String, String> storeFile(MultipartFile file) throws IOException;

  byte[] retrieveFile(String path, String filename) throws IOException;

  void deleteFile(String path, String filename);
}
