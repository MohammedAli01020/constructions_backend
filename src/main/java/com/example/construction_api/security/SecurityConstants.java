package com.example.construction_api.security;

public class SecurityConstants {
    public static final String SECRET = "VerySecretiveKey";
//    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final int EXPIRATION_TIME = 100; // 100 years

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static final String SIGN_UP_URL = "/api/users/create";

    public static final String LOGIN_URL = "/login";

    public static final String ROLES_CLAIM = "roles";

    public static final String ROLE_MANAGER = "manager";

    public static final String ROLE_ADMIN= "admin";

    public static final String ROLE_USER= "user";

    public static final String EMPLOYER_BY_ID_URL= "/api/employers/id/***";


    public static final String EMPLOYEES_MODIFY_URL = "/api/employees/modify";
    public static final String MACHINES_MODIFY_URL = "/api/machines/modify";









}