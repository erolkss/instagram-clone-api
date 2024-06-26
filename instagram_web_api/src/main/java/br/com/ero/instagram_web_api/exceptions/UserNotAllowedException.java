package br.com.ero.instagram_web_api.exceptions;

public class UserNotAllowedException extends RuntimeException{
    public UserNotAllowedException(String message) {
        super(message);
    }
}
