package com.hudel.web.backend.rest.web.service;

import com.hudel.web.backend.model.entity.Blog;
import com.hudel.web.backend.rest.web.model.request.CreateNewBlogRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BlogService {

  Blog createNewBlog(CreateNewBlogRequest request);

  Blog updateCoverImageById(String id, MultipartFile file) throws IOException;

  Blog updateContentImageById(String id, MultipartFile file) throws IOException;
}
