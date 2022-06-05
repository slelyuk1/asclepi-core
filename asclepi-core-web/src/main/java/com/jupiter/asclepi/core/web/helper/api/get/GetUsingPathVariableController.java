package com.jupiter.asclepi.core.web.helper.api.get;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public interface GetUsingPathVariableController<RequestType, ResponseType> {

    @GetMapping("/{id}")
    ResponseEntity<ResponseType> getOne(@NotNull @PathVariable("id") RequestType getRequest);

}
