package com.jupiter.asclepi.core.web.helper.api;

import com.jupiter.asclepi.core.model.request.client.EditClientRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public interface EditController<RequestType> {
    @PostMapping("/edit")
    ResponseEntity<?> edit(@NotNull @RequestBody RequestType editRequest);
}
