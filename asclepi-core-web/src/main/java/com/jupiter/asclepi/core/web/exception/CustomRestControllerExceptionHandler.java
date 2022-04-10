package com.jupiter.asclepi.core.web.exception;

import com.jupiter.asclepi.core.model.model.response.error.ErrorInfo;
import com.jupiter.asclepi.core.service.exception.AsclepiRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class CustomRestControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String SERVICE_UNAVAILABLE_MESSAGE = "Unfortunately, service is unavailable. Please, try later.";
    private static final String FIELD_VALIDATION_MESSAGE_TEMPLATE = "Field '%s' %s";

    @ExceptionHandler(AsclepiRuntimeException.class)
    public ResponseEntity<ErrorInfo> handleTransactionException(@NotNull AsclepiRuntimeException e) {
        Throwable cause = e.getCause();
        if (cause instanceof TransactionSystemException) {
            return handleTransactionException((TransactionSystemException) cause);
        }
        if (cause instanceof AuthenticationException) {
            handleAuthenticationException((AuthenticationException) cause);
        }
        log.warn("Not recognized exception wrapped in AsclepiRuntimeException occurred: ", e);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorInfo(SERVICE_UNAVAILABLE_MESSAGE));
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ErrorInfo> handleTransactionException(@NotNull TransactionSystemException e) {
        Throwable maybeConstraintViolation = e.getOriginalException();
        if (maybeConstraintViolation != null && maybeConstraintViolation.getCause() instanceof ConstraintViolationException) {
            return handleConstraintViolationException((ConstraintViolationException) maybeConstraintViolation.getCause());
        }
        log.warn("Not recognized TransactionSystemException exception occurred: ", e);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorInfo(e.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfo> handleConstraintViolationException(@NotNull ConstraintViolationException exception) {
        String message = exception.getConstraintViolations().stream()
                .map(violation -> String.format(FIELD_VALIDATION_MESSAGE_TEMPLATE, violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.joining(". "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(message));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorInfo> handleAuthenticationException(@NotNull AuthenticationException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorInfo(exception.getMessage()));
    }

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
