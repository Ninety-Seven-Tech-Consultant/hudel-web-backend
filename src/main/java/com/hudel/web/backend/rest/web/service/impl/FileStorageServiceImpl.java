package com.hudel.web.backend.rest.web.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.hudel.web.backend.config.properties.AmazonProperties;
import com.hudel.web.backend.model.constant.ErrorCode;
import com.hudel.web.backend.model.exception.BaseException;
import com.hudel.web.backend.rest.web.service.FileStorageService;
import com.hudel.web.backend.rest.web.util.FileUtil;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import java.io.IOException;

@Service
public class FileStorageServiceImpl implements FileStorageService {

  @Autowired
  private AmazonS3 amazonS3;

  @Autowired
  private AmazonProperties amazonProperties;

  @Override
  public Tuple3<String, String, String> storeFile(MultipartFile file) throws IOException {
    String filename = generateUniqueFilename(file.getOriginalFilename());
    FileUtil.validateFileNotEmpty(file);
    validateFileName(filename);
    PutObjectRequest request = new PutObjectRequest(amazonProperties.getBucketName(), filename,
        file.getInputStream(), generateMetadata(file));
    request.setCannedAcl(CannedAccessControlList.PublicRead);
    amazonS3.putObject(request);
    return Tuples.of(amazonProperties.getBucketName(), filename, file.getContentType());
  }

  @Override
  public byte[] retrieveFile(String path, String filename) throws IOException {
    S3Object object = amazonS3.getObject(path, filename);
    return IOUtils.toByteArray(object.getObjectContent());
  }

  @Override
  public void deleteFile(String path, String filename) {
    amazonS3.deleteObject(path, filename);
  }

  private String generateUniqueFilename(String originalFilename) {
    String extension = "." + FilenameUtils.getExtension(originalFilename).toLowerCase();
    String filename = RandomString.make(12);
    while (amazonS3.doesObjectExist(amazonProperties.getBucketName(), filename)) {
      filename = RandomString.make(12);
    }
    return filename + extension;
  }

  private void validateFileName(String filename) {
    if (filename.contains("..")) {
      throw new BaseException(ErrorCode.FILENAME_INVALID);
    }
  }

  private ObjectMetadata generateMetadata(MultipartFile file) {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.addUserMetadata("Content-Type", file.getContentType());
    objectMetadata.addUserMetadata("Content-Length", String.valueOf(file.getSize()));
    return objectMetadata;
  }
}
