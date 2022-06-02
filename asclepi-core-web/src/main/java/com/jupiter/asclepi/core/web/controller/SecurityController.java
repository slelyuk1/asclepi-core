package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.other.AuthenticationRequest;
import com.jupiter.asclepi.core.web.configuration.WebSecurityConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface SecurityController {
    @PostMapping(WebSecurityConfiguration.AUTHENTICATION_ENDPOINT_SUFFIX)
    ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequest request);
}
