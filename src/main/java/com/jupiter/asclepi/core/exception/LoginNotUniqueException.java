package com.jupiter.asclepi.core.exception;

public class LoginNotUniqueException extends AsclepiRuntimeException {
    private static final String MESSAGE = "Employee with login %s already exists!";

    public LoginNotUniqueException(String duplicatedLogin, Throwable cause) {
        super(String.format(MESSAGE, duplicatedLogin), cause);
    }
}
