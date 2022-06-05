package com.jupiter.asclepi.core.web.helper.api.get;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public interface GetUsingRequestBodyController<RequestType, ResponseType> {

    @GetMapping("/")
    ResponseEntity<ResponseType> getOne(@NotNull @RequestBody RequestType getRequest);

}
