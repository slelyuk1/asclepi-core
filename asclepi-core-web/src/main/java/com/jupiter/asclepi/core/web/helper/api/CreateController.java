package com.jupiter.asclepi.core.web.helper.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public interface CreateController<RequestType, ResponseType> {

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<ResponseType> create(@NotNull @RequestBody RequestType createRequest);

}
