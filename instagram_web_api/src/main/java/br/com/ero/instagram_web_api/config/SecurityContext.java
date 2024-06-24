package br.com.ero.instagram_web_api.config;

public class SecurityContext {
    public static final String JWT_KEY = System.getenv("JWT_KEY");
    public static final String HEADER = "Authorization";

}

