package com.hudel.web.backend.rest.web.service;

import com.hudel.web.backend.model.entity.SystemParameter;
import com.hudel.web.backend.rest.web.model.request.UpsertSystemParameterRequest;

public interface SystemParameterService {

  SystemParameter create(UpsertSystemParameterRequest request);

  SystemParameter findByKey(String key);

  SystemParameter update(UpsertSystemParameterRequest request);

  void deleteByKey(String key);
}
