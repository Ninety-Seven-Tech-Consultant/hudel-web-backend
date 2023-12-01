package com.hudel.web.backend.rest.web.service;

import com.hudel.web.backend.model.entity.Blog;
import com.hudel.web.backend.rest.web.model.request.CreateNewBlogRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BlogService {

  Blog createNewBlog(CreateNewBlogRequest request);

  Blog updateCoverImageById(String id, MultipartFile file) throws IOException;

  Blog updateContentImageById(String id, MultipartFile file) throws IOException;

  void deleteById(String id);

  Page<Blog> findByTitle(Integer page, Integer size, String title);

  Blog findById(String id);

  List<Blog> getSuggestedBlogs(Integer size, String id);
}
