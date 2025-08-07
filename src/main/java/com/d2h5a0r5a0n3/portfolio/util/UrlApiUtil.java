package com.d2h5a0r5a0n3.portfolio.util;

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
    public static final String API_EDUCATION = "/api/education";
    public static final String API_EDUCATIONS = "/api/educations/**";
    public static final String API_GET_PROFICIENCIES = "/api/enum/proficiencies";
    public static final String API_GET_SKILL_CATEGORY = "/api/enum/skill-categories";
    public static final String API_GET_SKILL_ACTIONS = "/api/enum/skill-actions";
    public static final String API_GET_JOB_TYPE = "/api/enum/job-types";
    public static final String API_RESUME = "/api/resumes";
    public static final String API_RESUMES = "/api/resumes/**";
    public static final String API_RESUME_UPLOAD = "/api/resumes/upload";
}
