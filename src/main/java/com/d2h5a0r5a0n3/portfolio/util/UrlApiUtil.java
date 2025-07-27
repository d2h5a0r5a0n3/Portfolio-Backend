package com.d2h5a0r5a0n3.portfolio.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlApiUtil {
    public static final String API_EXPERIENCES = "/api/experiences/**";
    public static final String API_ADMIN = "ADMIN";
    public static final String API_PROJECTS = "/api/projects/**";
    public static final String API_PROJECT = "/api/projects";
    public static final String API_EXPERIENCE = "/api/experiences";
    public static final String API_LOGIN = "/api/login";
    public static final String API_LOGOUT = "/api/logout";
    public static final String API_CONTACT = "/api/contact/send";
    public static final String API_IS_ADMIN = "/api/is-admin";
    public static final String API_SESSION_STATUS = "/api/session/status";
    public static final String API_LOGOUT_SUCCESS = "/";
    public static final String API_EDUCATION="/api/education";
    public static final String API_EDUCATIONS="/api/educations/**";
}
