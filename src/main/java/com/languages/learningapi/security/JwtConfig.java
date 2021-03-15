package com.languages.learningapi.security;

public class JwtConfig {
    public static final String authUrl = "signin";

    public static final String header = "Authorization";

    public static final String prefix = "Bearer ";

    public static final int expiration = 24*60*60;

    public static final String secret = "lnldsMOIIidsifmdsfiooi3245rlkmnsflkwoier245t43re!2ds$";
}