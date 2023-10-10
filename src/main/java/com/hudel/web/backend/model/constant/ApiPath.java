package com.hudel.web.backend.model.constant;

public class ApiPath {

  public static final String BASE_PATH_API = "/api";

  public static final String BASE_PATH_AUTHENTICATION = BASE_PATH_API + "/auth";

  public static final String REGISTER = "/register";

  public static final String LOGIN = "/login";

  public static final String BASE_PATH_EMAIL = BASE_PATH_API + "/email";

  public static final String EMAIL_SEND_TEST = "/test";

  public static final String BASE_PATH_LANDING_PAGE = BASE_PATH_API + "/landing-page";

  public static final String LANDING_PAGE_SIGN_UP = "/sign-up/{email}";
}
