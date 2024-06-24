package br.com.ero.instagram_web_api.exceptions.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessageException {

    private String path;
    private String method;
    private int status;
    private String statusText;
    private String message;
    private Map<String, String> errors;

    public ErrorMessageException() {
    }

    public ErrorMessageException(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
    }

    public ErrorMessageException(HttpServletRequest httpServletRequest, HttpStatus httpStatus, String message, BindingResult bindingResult, MessageSource messageSource) {
        this.path = httpServletRequest.getRequestURI();
        this.method = httpServletRequest.getMethod();
        this.status = httpStatus.value();
        this.statusText = httpStatus.getReasonPhrase();
        this.message = message;
        addErrors(bindingResult, messageSource, httpServletRequest.getLocale());
    }

    public ErrorMessageException(HttpStatus status, String authenticationFailed) {
        this.status = status.value();
        this.message = authenticationFailed;
    }

    private void addErrors(BindingResult bindingResult, MessageSource messageSource, Locale locale) {
        this.errors = new HashMap<>();
        for (FieldError fieldError:bindingResult.getFieldErrors()) {
            String code = Objects.requireNonNull(fieldError.getCodes())[0];
            String message = messageSource.getMessage(code, fieldError.getArguments(), locale);
            this.errors.put(fieldError.getField(), message);

        }
    }

    private void addErrors(BindingResult bindingResult) {
        this.errors = new HashMap<>();
        for (FieldError fieldError:bindingResult.getFieldErrors()) {
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());

        }
    }
}

