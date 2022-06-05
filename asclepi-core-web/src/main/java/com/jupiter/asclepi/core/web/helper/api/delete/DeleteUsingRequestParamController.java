package com.jupiter.asclepi.core.web.helper.api.delete;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

public interface DeleteUsingRequestParamController<RequestType> {

    @DeleteMapping("/")
    ResponseEntity<Void> delete(@NotNull @RequestParam RequestType deleteRequest);

}
