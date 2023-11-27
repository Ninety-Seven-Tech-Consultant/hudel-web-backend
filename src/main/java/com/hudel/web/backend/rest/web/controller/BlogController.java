package com.hudel.web.backend.rest.web.controller;

import com.hudel.web.backend.model.constant.ApiPath;
import com.hudel.web.backend.model.entity.Blog;
import com.hudel.web.backend.rest.web.model.request.CreateNewBlogRequest;
import com.hudel.web.backend.rest.web.model.response.BlogResponse;
import com.hudel.web.backend.rest.web.model.response.rest.RestSingleResponse;
import com.hudel.web.backend.rest.web.service.BlogService;
import com.hudel.web.backend.rest.web.util.DateUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Blog", description = "Blog Service API")
@Validated
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_BLOG)
public class BlogController extends BaseController {

  @Autowired
  private BlogService blogService;

  @PostMapping
  public RestSingleResponse<BlogResponse> createNewBlog(@RequestBody CreateNewBlogRequest request) {
    Blog blog = blogService.createNewBlog(request);
    return toSingleResponse(toBlogResponse(blog));
  }

  private BlogResponse toBlogResponse(Blog blog) {
    BlogResponse response = new BlogResponse();
    BeanUtils.copyProperties(blog, response);
    response.setDatePublished(DateUtil.toDateOnlyFormat(blog.getDatePublished()));
    response.setCoverImageUrl(blog.getCoverImage().getUrl());
    response.setContentImageUrl(blog.getContentImage().getUrl());
    return response;
  }
}
