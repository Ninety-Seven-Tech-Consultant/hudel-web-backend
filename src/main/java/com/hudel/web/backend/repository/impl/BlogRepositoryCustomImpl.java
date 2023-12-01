package com.hudel.web.backend.repository.impl;

import com.hudel.web.backend.model.entity.Blog;
import com.hudel.web.backend.repository.BlogRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class BlogRepositoryCustomImpl implements BlogRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Page<Blog> searchByTitle(String title, PageRequest pageRequest) {
    Query query = new Query().addCriteria(where("title")
            .regex(String.format(".*%s.*", title), "i"))
        .with(Sort.by(DESC, "datePublished"))
        .with(pageRequest);
    List<Blog> blogs = mongoTemplate.find(query, Blog.class);
    return PageableExecutionUtils.getPage(blogs, pageRequest,
        () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Blog.class));
  }

  @Override
  public List<Blog> findSuggestedBlogsById(Integer size, String id) {
    Query query = new Query().addCriteria(where("id").ne(id))
        .with(Sort.by(DESC, "datePublished"))
        .limit(size);
    return mongoTemplate.find(query, Blog.class);
  }
}
