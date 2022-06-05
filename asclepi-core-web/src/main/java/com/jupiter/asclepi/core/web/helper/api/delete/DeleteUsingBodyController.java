package com.jupiter.asclepi.core.web.helper.api.delete;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public interface DeleteUsingBodyController<RequestType> {

    @DeleteMapping("/")
    ResponseEntity<Void> delete(@NotNull @RequestBody RequestType deleteRequest);

}
