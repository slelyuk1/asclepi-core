package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.configuration.SecurityConfiguration;
import com.jupiter.asclepi.core.model.request.other.AuthenticationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface SecurityController {
    @PostMapping(SecurityConfiguration.AUTHENTICATION_ENDPOINT_SUFFIX)
    ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequest request);
}
