package com.jupiter.asclepi.core.exception.shared;

import com.jupiter.asclepi.core.exception.AsclepiRuntimeException;

public class NonExistentIdException extends AsclepiRuntimeException {
    private static final String MESSAGE = "%s with id %s doesn't exist!";

    public NonExistentIdException(String entityName, Object id) {
        super(String.format(MESSAGE, entityName, id));
    }
}
