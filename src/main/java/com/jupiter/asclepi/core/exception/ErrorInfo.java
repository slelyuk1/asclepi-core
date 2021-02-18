package com.jupiter.asclepi.core.exception;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class ErrorInfo {
    String message;
}
