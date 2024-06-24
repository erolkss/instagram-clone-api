package br.com.ero.instagram_web_api.exceptions;

public class UserEmailUniqueViolationException extends RuntimeException{
    public UserEmailUniqueViolationException(String message) {
        super(message);
    }
}
