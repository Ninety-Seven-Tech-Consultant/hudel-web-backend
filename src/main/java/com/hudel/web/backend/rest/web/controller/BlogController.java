package com.hudel.web.backend.rest.web.controller;

import com.hudel.web.backend.model.constant.ApiPath;
import com.hudel.web.backend.model.entity.Blog;
import com.hudel.web.backend.rest.web.model.request.CreateNewBlogRequest;
import com.hudel.web.backend.rest.web.model.response.BlogResponse;
import com.hudel.web.backend.rest.web.model.response.rest.RestPageResponse;
import com.hudel.web.backend.rest.web.model.response.rest.RestSingleResponse;
import com.hudel.web.backend.rest.web.service.BlogService;
import com.hudel.web.backend.rest.web.util.DateUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

  @PostMapping(value = ApiPath.BLOG_UPDATE_COVER_IMAGE_BY_ID)
  public RestSingleResponse<BlogResponse> updateCoverImageById(@PathVariable("id") String id,
      @RequestParam("file") MultipartFile file) throws IOException {
    Blog blog = blogService.updateCoverImageById(id, file);
    return toSingleResponse(toBlogResponse(blog));
  }

  @PostMapping(value = ApiPath.BLOG_UPDATE_CONTENT_IMAGE_BY_ID)
  public RestSingleResponse<BlogResponse> updateContentImageById(@PathVariable("id") String id,
      @RequestParam("file") MultipartFile file) throws IOException {
    Blog blog = blogService.updateContentImageById(id, file);
    return toSingleResponse(toBlogResponse(blog));
  }

  @PostMapping(value = ApiPath.BLOG_FIND_BY_TITLE)
  public RestPageResponse<BlogResponse> findByTitle(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size, @RequestParam(required = false) String title) {
    Page<Blog> blogs = blogService.findByTitle(page, size, title);
    List<BlogResponse> content = blogs.getContent().stream()
        .map(this::toBlogResponse)
        .collect(Collectors.toList());
    return toPageResponse(content, blogs);
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
