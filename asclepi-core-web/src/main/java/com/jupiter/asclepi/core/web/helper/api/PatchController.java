package com.jupiter.asclepi.core.web.helper.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public interface PatchController<RequestType, ResponseType> {

    @PatchMapping("/")
    ResponseEntity<ResponseType> edit(@NotNull @RequestBody RequestType editRequest);
}