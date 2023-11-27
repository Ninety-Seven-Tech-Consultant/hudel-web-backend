package com.hudel.web.backend.rest.web.service.impl;

import com.hudel.web.backend.config.properties.SysparamProperties;
import com.hudel.web.backend.model.entity.Blog;
import com.hudel.web.backend.model.entity.BlogImage;
import com.hudel.web.backend.repository.BlogRepository;
import com.hudel.web.backend.rest.web.model.request.CreateNewBlogRequest;
import com.hudel.web.backend.rest.web.service.BlogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {

  @Autowired
  private BlogRepository blogRepository;

  @Autowired
  private SysparamProperties sysparamProperties;

  @Override
  public Blog createNewBlog(CreateNewBlogRequest request) {
    return blogRepository.save(buildNewBlog(request));
  }

  private Blog buildNewBlog(CreateNewBlogRequest request) {
    Blog blog = new Blog();
    BeanUtils.copyProperties(request, blog);
    blog.setCoverImage(buildDefaultBlogImage());
    blog.setContentImage(buildDefaultBlogImage());
    return blog;
  }

  private BlogImage buildDefaultBlogImage() {
    return BlogImage.builder()
        .imageId(sysparamProperties.getBlogDefaultImageId())
        .url(sysparamProperties.getImageRetrieveUrl() + sysparamProperties.getBlogDefaultImageId())
        .isDefault(true)
        .build();
  }
}
