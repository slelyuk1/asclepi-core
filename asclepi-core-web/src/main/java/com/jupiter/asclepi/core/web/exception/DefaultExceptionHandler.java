package com.jupiter.asclepi.core.web.exception;

import com.jupiter.asclepi.core.model.response.error.ErrorInfo;
import com.jupiter.asclepi.core.service.exception.AsclepiRuntimeException;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

@Slf4j
@AllArgsConstructor
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String SERVICE_UNAVAILABLE_MESSAGE = "Unfortunately, service is unavailable. Please, try later.";

    private final BusinessExceptionHandler businessExceptionHandler;

    @ExceptionHandler(AsclepiRuntimeException.class)
    public ResponseEntity<ErrorInfo> handleTransactionException(@NotNull AsclepiRuntimeException e) {
        Throwable cause = e.getCause();
        if (cause instanceof TransactionSystemException transactionSystemException) {
            return handleTransactionException(transactionSystemException);
        }
        if (cause instanceof AuthenticationException authenticationException) {
            businessExceptionHandler.handleAuthenticationException(authenticationException);
        }
        if (cause instanceof NonExistentIdException nonExistentIdException) {
            businessExceptionHandler.handleNonExistentIdException(nonExistentIdException);
        }
        log.warn("Not recognized exception wrapped in AsclepiRuntimeException occurred: ", e);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorInfo(SERVICE_UNAVAILABLE_MESSAGE));
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ErrorInfo> handleTransactionException(@NotNull TransactionSystemException e) {
        Throwable maybeConstraintViolation = e.getOriginalException();
        if (maybeConstraintViolation != null
                && maybeConstraintViolation.getCause() instanceof ConstraintViolationException constraintViolationException) {
            return businessExceptionHandler.handleConstraintViolationException(constraintViolationException);
        }
        log.warn("Not recognized TransactionSystemException exception occurred: ", e);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorInfo(e.getMessage()));
    }

}
