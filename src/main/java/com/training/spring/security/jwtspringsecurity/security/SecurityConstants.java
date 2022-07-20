package com.training.spring.security.jwtspringsecurity.security;

public class SecurityConstants {

    public static final int EXPIRATION_TIME = 432_000_000; //5 days
    public static final String TOKEN_HEADER = "Bearer ";
    public static final String JWT_TOKEN_HEADER ="Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token connot be verified";
    public static final String GET_ARRAYS_LLC = "Get Arrays, LLC";
    public static final String GET_ARRAYS_ADMINISTRATION = "User Management Portal";
    public static final String AUTHORITIES = "Authorities";
    public static final String FORBIDDEN_MESSAGE ="You need loging for access to this page";
    public static final String ACCESS_DENIED_MESSAGE = "You dont have permission to access to this page";
    public static final String OPTION_HTTP_METHODS = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/user/login","/user/register","/user/resetPassword/**","/user/image/**"};



}
