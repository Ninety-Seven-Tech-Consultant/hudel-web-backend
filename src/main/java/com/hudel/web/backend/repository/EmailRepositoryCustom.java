package com.hudel.web.backend.repository;

import com.hudel.web.backend.model.entity.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface EmailRepositoryCustom {

  List<Email> findAllByDomainEndsWith(String domain);

  Page<Email> searchByEmail(String email, PageRequest pageRequest);
}
