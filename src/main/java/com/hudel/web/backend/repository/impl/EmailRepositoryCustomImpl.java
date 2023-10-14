package com.hudel.web.backend.repository.impl;

import com.hudel.web.backend.model.entity.Email;
import com.hudel.web.backend.repository.EmailRepositoryCustom;
import com.hudel.web.backend.rest.web.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class EmailRepositoryCustomImpl implements EmailRepositoryCustom {

  @Autowired
  private StringUtil stringUtil;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public List<Email> findAllByDomainEndsWith(String domain) {
    Query query = new Query().addCriteria(where("email")
        .regex(String.format(".*%s", domain), "i"));
    return mongoTemplate.find(query, Email.class);
  }

  @Override
  public Page<Email> searchByEmail(String email, PageRequest pageRequest) {
    Query query = new Query().addCriteria(where("email")
        .regex(String.format(".*%s.*", email), "i"))
        .with(pageRequest);
    List<Email> emails = mongoTemplate.find(query, Email.class);
    return PageableExecutionUtils.getPage(emails, pageRequest,
        () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Email.class));
  }
}
