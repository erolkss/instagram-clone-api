package br.com.ero.instagram_web_api.exceptions.handler;

import br.com.ero.instagram_web_api.exceptions.EmailNoExistsException;
import br.com.ero.instagram_web_api.exceptions.UserEmailUniqueViolationException;
import br.com.ero.instagram_web_api.exceptions.UsernameUniqueViolationException;
import br.com.ero.instagram_web_api.exceptions.dto.ErrorMessageException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserEmailUniqueViolationException.class, UsernameUniqueViolationException.class})
    public ResponseEntity<ErrorMessageException> useEmailUniqueViolationExceptionHandler(RuntimeException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageException(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> validationExceptionsHandler(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            } else {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmailNoExistsException.class})
    public ResponseEntity<ErrorMessageException> emailNoExistsExceptionHandler(EmailNoExistsException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageException(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

}
