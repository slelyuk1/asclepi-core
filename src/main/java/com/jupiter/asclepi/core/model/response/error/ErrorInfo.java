package com.jupiter.asclepi.core.model.response.error;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class ErrorInfo {
    String message;
}
