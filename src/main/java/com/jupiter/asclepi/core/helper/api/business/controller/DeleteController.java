package com.jupiter.asclepi.core.helper.api.business.controller;

import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;

public interface DeleteController<RequestType> {
    ResponseEntity<?> delete(@NotNull RequestType deleteRequest);
}
