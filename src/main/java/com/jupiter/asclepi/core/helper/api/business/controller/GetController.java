package com.jupiter.asclepi.core.helper.api.business.controller;

import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;

public interface GetController<RequestType, ResponseType> {
    ResponseEntity<ResponseType> getOne(@NotNull RequestType getRequest);
}
