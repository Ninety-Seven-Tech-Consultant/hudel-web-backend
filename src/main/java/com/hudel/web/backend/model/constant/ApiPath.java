package com.hudel.web.backend.model.constant;

public class ApiPath {

  public static final String BASE_PATH_API = "/api";

  public static final String BASE_PATH_AUTHENTICATION = BASE_PATH_API + "/auth";
  public static final String REGISTER = "/register";
  public static final String LOGIN = "/login";

  public static final String BASE_PATH_SYSTEM_PARAMETER = BASE_PATH_API + "/system-parameters";
  public static final String SYSTEM_PARAMETER_FIND_BY_KEY = "/{key}";
  public static final String SYSTEM_PARAMETER_DELETE_BY_KEY = "/{key}";

  public static final String BASE_PATH_LANDING_PAGE = BASE_PATH_API + "/landing-page";
  public static final String LANDING_PAGE_SIGN_UP = "/sign-up/{email}";

  public static final String BASE_PATH_EMAIL = BASE_PATH_API + "/emails";
  public static final String SEND_WELCOME_EMAIL = "/{email}";
  public static final String GET_WHITELISTED_DOMAINS = "/whitelisted-domains";
  public static final String WHITELIST_DOMAIN = "/whitelisted-domains/{domain}";
  public static final String DELETE_WHITELISTED_DOMAIN = "/whitelisted-domains/{domain}";

  public static final String BASE_PATH_IMAGE = BASE_PATH_API + "/images";
  public static final String IMAGE_RETRIEVE_BY_ID = "/{id}";
  public static final String IMAGE_DELETE_BY_ID = "/{id}";

  public static final String BASE_PATH_BLOG = BASE_PATH_API + "/blogs";
  public static final String BLOG_UPDATE_COVER_IMAGE_BY_ID = "/{id}/update-cover-image";
}
