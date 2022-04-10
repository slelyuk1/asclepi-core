package com.jupiter.asclepi.core.web.helper.api;

import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;

public interface EditController<RequestType> {
    ResponseEntity<?> edit(@NotNull RequestType editRequest);
}
