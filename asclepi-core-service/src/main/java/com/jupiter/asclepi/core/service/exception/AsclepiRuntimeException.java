package com.jupiter.asclepi.core.service.exception;

public class AsclepiRuntimeException extends RuntimeException {

    public AsclepiRuntimeException(String message) {
        super(message);
    }

    public AsclepiRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AsclepiRuntimeException(Throwable cause) {
        super(cause);
    }
}
