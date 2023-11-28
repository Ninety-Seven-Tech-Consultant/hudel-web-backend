package com.hudel.web.backend.rest.web.service.impl;

import com.hudel.web.backend.config.properties.SysparamProperties;
import com.hudel.web.backend.model.constant.ErrorCode;
import com.hudel.web.backend.model.entity.Blog;
import com.hudel.web.backend.model.entity.BlogImage;
import com.hudel.web.backend.model.entity.File;
import com.hudel.web.backend.model.exception.BaseException;
import com.hudel.web.backend.repository.BlogRepository;
import com.hudel.web.backend.rest.web.model.request.CreateNewBlogRequest;
import com.hudel.web.backend.rest.web.service.BlogService;
import com.hudel.web.backend.rest.web.service.ImageService;
import com.hudel.web.backend.rest.web.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class BlogServiceImpl implements BlogService {

  @Autowired
  private BlogRepository blogRepository;

  @Autowired
  private ImageService imageService;

  @Autowired
  private StringUtil stringUtil;

  @Autowired
  private SysparamProperties sysparamProperties;

  @Override
  public Blog createNewBlog(CreateNewBlogRequest request) {
    return blogRepository.save(buildNewBlog(request));
  }

  @Override
  public Blog updateCoverImageById(String id, MultipartFile file) throws IOException {
    validateIdNotNull(id);
    Blog blog = findBlogById(id);
    File coverImage = imageService.uploadImage(file);
    updateCoverImage(blog, coverImage);
    return blogRepository.save(blog);
  }

  @Override
  public Blog updateContentImageById(String id, MultipartFile file) throws IOException {
    validateIdNotNull(id);
    Blog blog = findBlogById(id);
    File coverImage = imageService.uploadImage(file);
    updateContentImage(blog, coverImage);
    return blogRepository.save(blog);
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

  private void validateIdNotNull(String id) {
    if (stringUtil.isStringNullOrBlank(id)) {
      throw new BaseException(ErrorCode.ID_IS_NULL);
    }
  }

  private Blog findBlogById(String id) {
    Blog blog = blogRepository.findById(id).orElse(null);
    if (Objects.isNull(blog)) {
      throw new BaseException(ErrorCode.BLOG_NOT_FOUND);
    }
    return blog;
  }

  private void updateCoverImage(Blog blog, File file) {
    if (!blog.getCoverImage().isDefault()) {
      imageService.deleteImageById(blog.getCoverImage().getImageId());
    }
    blog.setCoverImage(toBlogImage(file));
  }

  private void updateContentImage(Blog blog, File file) {
    if (!blog.getContentImage().isDefault()) {
      imageService.deleteImageById(blog.getContentImage().getImageId());
    }
    blog.setContentImage(toBlogImage(file));
  }

  private BlogImage toBlogImage(File file) {
    return BlogImage.builder()
        .imageId(file.getFileId())
        .url(sysparamProperties.getImageRetrieveUrl() + file.getFileId())
        .isDefault(false)
        .build();
  }
}
