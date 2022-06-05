package com.jupiter.asclepi.core.web.exception;

import com.jupiter.asclepi.core.model.response.error.ErrorInfo;
import com.jupiter.asclepi.core.service.exception.employee.LoginNotUniqueException;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ControllerAdvice
public class BusinessExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String FIELD_VALIDATION_MESSAGE_TEMPLATE = "Field '%s' %s";

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfo> handleConstraintViolationException(@NotNull ConstraintViolationException exception) {
        String message = exception.getConstraintViolations().stream()
                .map(violation -> String.format(FIELD_VALIDATION_MESSAGE_TEMPLATE, violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.joining(". "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(message));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorInfo> handleAuthenticationException(@NotNull AuthenticationException exception) {
        // todo review this case
//        log.warn("AuthenticationException occurred:", e);
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorInfo(AUTHENTICATION_ERROR_MESSAGE));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorInfo(exception.getMessage()));
    }

    @ExceptionHandler(LoginNotUniqueException.class)
    public ResponseEntity<ErrorInfo> handleTransactionException(@NotNull LoginNotUniqueException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo(e.getMessage()));
    }

    @ExceptionHandler(NonExistentIdException.class)
    public ResponseEntity<ErrorInfo> handleTransactionException() {
        return ResponseEntity.notFound().header(CONTENT_TYPE, APPLICATION_JSON_VALUE).build();
    }

    // todo rethink errorinfo structure
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> String.format(FIELD_VALIDATION_MESSAGE_TEMPLATE, fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.joining(". "));
        return handleExceptionInternal(e, new ErrorInfo(message), headers, status, request);
    }

}
