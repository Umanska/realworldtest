package org.realworld.api;

import org.realworld.utils.PropertiesManagerUtils;

public final class Constants {

    public static final String API_BASE_URL = PropertiesManagerUtils.getProperty("apiBaseUrl");
    public static final String UI_BASE_URL = PropertiesManagerUtils.getProperty("uiBaseUrl");
    public static final String PASSWORD = PropertiesManagerUtils.getProperty("password");
    public static final String USER_NAME_PREFIX_API = "qatestapi";
    public static final String USER_NAME_PREFIX_UI = "qatestui";
    public static final String EMAIL_SUFFIX = "@gmail.com";
    public static final String USER_1_EMAIL = "qatestapi000@gmail.com";
    public static final String USER_2_EMAIL = "qatestapi111@gmail.com";
    public static final String USERNAME_OF_EXISTED_USER = "qa123";
}
