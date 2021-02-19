package com.jupiter.asclepi.core.helper.api.business.controller;

import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface EditController<RequestType> {
    ResponseEntity<?> edit(@NotNull RequestType editRequest);
}
