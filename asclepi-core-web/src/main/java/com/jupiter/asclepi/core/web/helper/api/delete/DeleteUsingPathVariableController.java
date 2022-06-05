package com.jupiter.asclepi.core.web.helper.api.delete;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;

public interface DeleteUsingPathVariableController<RequestType> {

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@NotNull @PathVariable("id") RequestType deleteRequest);

}
