package com.hudel.web.backend.rest.web.service.impl;

import com.hudel.web.backend.model.constant.ErrorCode;
import com.hudel.web.backend.model.entity.File;
import com.hudel.web.backend.model.exception.BaseException;
import com.hudel.web.backend.repository.FileRepository;
import com.hudel.web.backend.rest.web.service.FileStorageService;
import com.hudel.web.backend.rest.web.service.ImageService;
import com.hudel.web.backend.rest.web.util.FileUtil;
import com.hudel.web.backend.rest.web.util.ImageUtil;
import com.hudel.web.backend.rest.web.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import java.io.IOException;
import java.net.URLConnection;
import java.util.Objects;

@Service
public class ImageServiceImpl implements ImageService {

  @Autowired
  private FileRepository fileRepository;

  @Autowired
  private FileStorageService fileStorageService;

  @Autowired
  private ImageUtil imageUtil;

  @Autowired
  private StringUtil stringUtil;

  @Override
  public File uploadImage(MultipartFile file) throws IOException {
    FileUtil.validateFileNotEmpty(file);
    validateFileTypeFromFileName(file.getOriginalFilename());
    Tuple3<String, String, String> tuple3 =
        fileStorageService.storeFile(imageUtil.compressImage(file));
    return fileRepository.save(File.builder()
        .fileId(stringUtil.generateFileId())
        .path(tuple3.getT1())
        .filename(tuple3.getT2())
        .filetype(tuple3.getT3())
        .build());
  }

  @Override
  public Tuple2<String, byte[]> retrieveImageById(String id) throws IOException {
    File file = getImageById(id);
    byte[] imageData = fileStorageService.retrieveFile(file.getPath(), file.getFilename());
    return Tuples.of(file.getFilename(), imageData);
  }

  @Override
  public void deleteImageById(String id) {
    File file = getImageById(id);
    fileStorageService.deleteFile(file.getPath(), file.getFilename());
    fileRepository.delete(file);
  }

  private void validateFileTypeFromFileName(String filename) {
    String mimetype = URLConnection.guessContentTypeFromName(filename);
    if (!mimetype.equals("image/png") && !mimetype.equals("image/jpeg")) {
      throw new BaseException(ErrorCode.FILETYPE_MUST_BE_IMAGE);
    }
  }

  private File getImageById(String id) {
    File file = fileRepository.findByFileId(id).orElse(null);
    if (Objects.isNull(file)) {
      throw new BaseException(ErrorCode.IMAGE_ID_DOES_NOT_EXISTS);
    }
    validateFileTypeFromFileName(file.getFilename());
    return file;
  }
}
