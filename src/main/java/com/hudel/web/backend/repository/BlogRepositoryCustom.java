package com.hudel.web.backend.repository;

import com.hudel.web.backend.model.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface BlogRepositoryCustom {

  Page<Blog> searchByTitle(String title, PageRequest pageRequest);
}
