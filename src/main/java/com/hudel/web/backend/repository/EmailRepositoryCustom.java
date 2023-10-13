package com.hudel.web.backend.repository;

import com.hudel.web.backend.model.entity.Email;

import java.util.List;

public interface EmailRepositoryCustom {

  List<Email> findAllByDomain(String domain);
}
