package com.jupiter.asclepi.core.exception.employee;

import com.jupiter.asclepi.core.exception.AsclepiRuntimeException;

public class LoginNotUniqueException extends AsclepiRuntimeException {
    private static final String MESSAGE = "Employee with login %s already exists!";

    public LoginNotUniqueException(String duplicatedLogin) {
        super(String.format(MESSAGE, duplicatedLogin));
    }
}
