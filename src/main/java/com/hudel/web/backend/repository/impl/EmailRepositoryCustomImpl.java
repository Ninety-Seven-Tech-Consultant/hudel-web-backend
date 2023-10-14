package com.hudel.web.backend.repository.impl;

import com.hudel.web.backend.model.entity.Email;
import com.hudel.web.backend.repository.EmailRepositoryCustom;
import com.hudel.web.backend.rest.web.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class EmailRepositoryCustomImpl implements EmailRepositoryCustom {

  @Autowired
  private StringUtil stringUtil;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public List<Email> findAllByDomain(String domain) {
    Query query = new Query();
    if (!stringUtil.isStringNullOrBlank(domain)) {
      query.addCriteria(where("email").regex(String.format(".*%s", domain), "i"));
    }
    return mongoTemplate.find(query, Email.class);
  }
}
