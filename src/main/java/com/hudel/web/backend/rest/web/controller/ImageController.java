package com.hudel.web.backend.rest.web.controller;

import com.hudel.web.backend.model.constant.ApiPath;
import com.hudel.web.backend.model.entity.File;
import com.hudel.web.backend.rest.web.model.response.ImageResponse;
import com.hudel.web.backend.rest.web.model.response.rest.RestBaseResponse;
import com.hudel.web.backend.rest.web.model.response.rest.RestSingleResponse;
import com.hudel.web.backend.rest.web.service.ImageService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.function.Tuple2;

import java.io.IOException;
import java.net.URLConnection;

@Api(value = "Image", description = "Image Service API")
@Validated
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_IMAGE)
public class ImageController extends BaseController {

  @Autowired
  private ImageService imageService;

  @PostMapping
  public RestSingleResponse<ImageResponse> upload(@RequestParam("file") MultipartFile file)
      throws IOException {
    File image = imageService.uploadImage(file);
    return toSingleResponse(toImageResponse(image));
  }

  @GetMapping(value = ApiPath.IMAGE_RETRIEVE_BY_ID)
  public ResponseEntity<byte[]> retrieveImageById(@PathVariable("id") String id)
      throws IOException {
    Tuple2<String, byte[]> tuple2 = imageService.retrieveImageById(id);
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(getFileTypeFromFileName(tuple2.getT1())))
        .body(tuple2.getT2());
  }

  @DeleteMapping(value = ApiPath.IMAGE_DELETE_BY_ID)
  public RestBaseResponse deleteImageById(@PathVariable("id") String id) {
    imageService.deleteImageById(id);
    return toBaseResponse();
  }

  private ImageResponse toImageResponse(File file) {
    ImageResponse response = new ImageResponse();
    BeanUtils.copyProperties(file, response);
    return response;
  }

  private String getFileTypeFromFileName(String filename) {
    return URLConnection.guessContentTypeFromName(filename);
  }
}
