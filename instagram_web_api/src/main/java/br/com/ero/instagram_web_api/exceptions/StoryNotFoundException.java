package br.com.ero.instagram_web_api.exceptions;

public class StoryNotFoundException extends RuntimeException {
    public StoryNotFoundException(String message) {
        super(message);
    }
}
