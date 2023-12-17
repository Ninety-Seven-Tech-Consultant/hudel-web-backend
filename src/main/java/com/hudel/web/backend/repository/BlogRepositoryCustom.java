package com.hudel.web.backend.repository;

import com.hudel.web.backend.model.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BlogRepositoryCustom {

  Page<Blog> searchByTitle(String title, PageRequest pageRequest);

  List<Blog> findSuggestedBlogsById(Integer size, String id);
}
