package br.com.ero.instagram_web_api.exceptions;

public class EmailNoExistsException extends RuntimeException{
    public EmailNoExistsException(String message) {
        super(message);
    }
}
