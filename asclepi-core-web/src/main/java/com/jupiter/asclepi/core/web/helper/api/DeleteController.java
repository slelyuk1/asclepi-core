package com.jupiter.asclepi.core.web.helper.api;

import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;

public interface DeleteController<RequestType> {
    ResponseEntity<?> delete(@NotNull RequestType deleteRequest);
}
