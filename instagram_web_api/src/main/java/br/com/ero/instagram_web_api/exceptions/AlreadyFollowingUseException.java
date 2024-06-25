package br.com.ero.instagram_web_api.exceptions;

public class AlreadyFollowingUseException extends RuntimeException {
    public AlreadyFollowingUseException(String message) {
        super(message);
    }
}
