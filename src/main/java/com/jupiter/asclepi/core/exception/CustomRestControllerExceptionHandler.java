package com.jupiter.asclepi.core.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

@ControllerAdvice
public class CustomRestControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String SERVICE_UNAVAILABLE_MESSAGE = "Unfortunately, service is unavailable. Please, try later.";
    private static final String FIELD_VALIDATION_MESSAGE_TEMPLATE = "Field '%s' %s";


    // todo review
    @ExceptionHandler(AsclepiRuntimeException.class)
    public ResponseEntity<ErrorInfo> handleTransactionException(@NotNull AsclepiRuntimeException e) {
        Throwable cause = e.getCause();
        if (cause instanceof TransactionSystemException) {
            return handleTransactionException((TransactionSystemException) cause);
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorInfo(SERVICE_UNAVAILABLE_MESSAGE));
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ErrorInfo> handleTransactionException(@NotNull TransactionSystemException e) {
        Throwable maybeConstraintViolation = e.getOriginalException();
        if (maybeConstraintViolation != null && maybeConstraintViolation.getCause() instanceof ConstraintViolationException) {
            return handleConstraintViolationException((ConstraintViolationException) maybeConstraintViolation);
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorInfo(e.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return e.getBindingResult().getFieldErrors().stream().findAny()
                .map(fieldError -> String.format(FIELD_VALIDATION_MESSAGE_TEMPLATE, fieldError.getField(), fieldError.getDefaultMessage()))
                .map(message -> handleExceptionInternal(e, new ErrorInfo(message), headers, status, request))
                .orElseGet(() -> super.handleMethodArgumentNotValid(e, headers, status, request));
    }

    private ResponseEntity<ErrorInfo> handleConstraintViolationException(ConstraintViolationException exception) {
        String message = exception.getConstraintViolations().stream()
                .map(violation -> String.format(FIELD_VALIDATION_MESSAGE_TEMPLATE, violation.getPropertyPath().toString(), violation.getMessage()))
                .findAny()
                .orElse("Unknown constraint violation!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(message));
    }
}
