package com.hudel.web.backend.rest.web.service;

import com.hudel.web.backend.model.entity.Blog;
import com.hudel.web.backend.rest.web.model.request.CreateNewBlogRequest;

public interface BlogService {

  Blog createNewBlog(CreateNewBlogRequest request);
}
