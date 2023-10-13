package com.hudel.web.backend.rest.web.controller;

import com.hudel.web.backend.model.constant.ApiPath;
import com.hudel.web.backend.model.entity.SystemParameter;
import com.hudel.web.backend.rest.web.model.request.UpsertSystemParameterRequest;
import com.hudel.web.backend.rest.web.model.response.SystemParameterResponse;
import com.hudel.web.backend.rest.web.model.response.rest.RestBaseResponse;
import com.hudel.web.backend.rest.web.model.response.rest.RestSingleResponse;
import com.hudel.web.backend.rest.web.service.SystemParameterService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "System Parameter", description = "System Parameter Service API")
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_SYSTEM_PARAMETER)
public class SystemParameterController extends BaseController {

  @Autowired
  private SystemParameterService systemParameterService;

  @PostMapping
  public RestSingleResponse<SystemParameterResponse> create(
      @Valid @RequestBody UpsertSystemParameterRequest request) {
    SystemParameter systemParameter = systemParameterService.create(request);
    return toSingleResponse(toSystemParameterResponse(systemParameter));
  }

  @GetMapping(value = ApiPath.SYSTEM_PARAMETER_FIND_BY_KEY)
  public RestSingleResponse<SystemParameterResponse> findByKey(@PathVariable("key") String key) {
    SystemParameter systemParameter = systemParameterService.findByKey(key);
    return toSingleResponse(toSystemParameterResponse(systemParameter));
  }

  @DeleteMapping(value = ApiPath.SYSTEM_PARAMETER_DELETE_BY_KEY)
  public RestBaseResponse deleteByKey(@PathVariable("key") String key) {
    systemParameterService.deleteByKey(key);
    return toBaseResponse();
  }

  private SystemParameterResponse toSystemParameterResponse(SystemParameter systemParameter) {
    SystemParameterResponse response = new SystemParameterResponse();
    BeanUtils.copyProperties(systemParameter, response);
    return response;
  }
}
