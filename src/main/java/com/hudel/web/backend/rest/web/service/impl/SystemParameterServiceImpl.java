package com.hudel.web.backend.rest.web.service.impl;

import com.hudel.web.backend.model.constant.ErrorCode;
import com.hudel.web.backend.model.constant.SystemParameterType;
import com.hudel.web.backend.model.entity.SystemParameter;
import com.hudel.web.backend.model.exception.BaseException;
import com.hudel.web.backend.repository.SystemParameterRepository;
import com.hudel.web.backend.rest.web.model.request.UpsertSystemParameterRequest;
import com.hudel.web.backend.rest.web.service.SystemParameterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SystemParameterServiceImpl implements SystemParameterService {

  @Autowired
  private SystemParameterRepository systemParameterRepository;

  @Override
  public SystemParameter create(UpsertSystemParameterRequest request) {
    validateUpsertSystemParameterRequest(request);
    if (systemParameterRepository.existsByKey(request.getKey())) {
      throw new BaseException(ErrorCode.SYSTEM_PARAMETER_TITLE_ALREADY_EXISTS);
    }
    return systemParameterRepository.save(buildSystemParameter(request));
  }

  @Override
  public SystemParameter findByKey(String key) {
    SystemParameter systemParameter = systemParameterRepository.findByKey(key);
    if (Objects.isNull(systemParameter)) {
      throw new BaseException(ErrorCode.SYSTEM_PARAMETER_NOT_FOUND);
    }
    return systemParameter;
  }

  @Override
  public SystemParameter update(UpsertSystemParameterRequest request) {
    validateUpsertSystemParameterRequest(request);
    SystemParameter systemParameter = systemParameterRepository.findByKey(request.getKey());
    if (Objects.isNull(systemParameter)) {
      throw new BaseException(ErrorCode.SYSTEM_PARAMETER_NOT_FOUND);
    }
    updateSystemParameterFromRequest(request, systemParameter);
    return systemParameterRepository.save(systemParameter);
  }

  @Override
  public void deleteByKey(String key) {
    SystemParameter systemParameter = systemParameterRepository.findByKey(key);
    if (Objects.isNull(systemParameter)) {
      throw new BaseException(ErrorCode.SYSTEM_PARAMETER_NOT_FOUND);
    }
    systemParameterRepository.delete(systemParameter);
  }

  private void validateUpsertSystemParameterRequest(UpsertSystemParameterRequest request) {
    if (!SystemParameterType.contains(request.getType())) {
      throw new BaseException(ErrorCode.SYSTEM_PARAMETER_TYPE_INVALID);
    } else if (SystemParameterType.STRING.getType().equals(request.getType()) &&
        !(request.getData() instanceof String)) {
      throw new BaseException(ErrorCode.SYSTEM_PARAMETER_DATA_INVALID);
    } else if (SystemParameterType.INTEGER.getType().equals(request.getType()) &&
        !(request.getData() instanceof Integer)) {
      throw new BaseException(ErrorCode.SYSTEM_PARAMETER_DATA_INVALID);
    }
  }

  private SystemParameter buildSystemParameter(UpsertSystemParameterRequest request) {
    SystemParameter systemParameter = new SystemParameter();
    BeanUtils.copyProperties(request, systemParameter);
    return systemParameter;
  }

  private SystemParameter updateSystemParameterFromRequest(UpsertSystemParameterRequest request,
      SystemParameter systemParameter) {
    BeanUtils.copyProperties(request, systemParameter);
    return systemParameter;
  }
}
