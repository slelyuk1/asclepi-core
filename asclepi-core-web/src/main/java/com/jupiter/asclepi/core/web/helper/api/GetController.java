package com.jupiter.asclepi.core.web.helper.api;

import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public interface GetController<RequestType, ResponseType> {
    ResponseEntity<ResponseType> getOne(@NotNull RequestType getRequest);
}
