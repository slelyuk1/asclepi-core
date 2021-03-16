package com.jupiter.asclepi.core.rest.controller.util;

import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public final class ControllerUtils {

    private ControllerUtils() {
        throw new IllegalStateException("ControllerUtils class mustn't be instantiated");
    }

    public static <T> ResponseEntity<T> notFoundResponse() {
        return ResponseEntity.notFound().header(CONTENT_TYPE, APPLICATION_JSON_VALUE).build();
    }
}
