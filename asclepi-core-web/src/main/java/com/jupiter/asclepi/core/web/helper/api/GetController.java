package com.jupiter.asclepi.core.web.helper.api;

import com.jupiter.asclepi.core.model.response.client.ClientInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@SuppressWarnings("unused")
public interface GetController<RequestType, ResponseType> {
    @GetMapping("/{clientId}")
    ResponseEntity<ResponseType> getOne(@NotNull @PathVariable("clientId") RequestType getRequest);
}
