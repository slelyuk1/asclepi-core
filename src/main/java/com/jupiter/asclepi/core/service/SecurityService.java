package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.model.request.other.AuthenticationRequest;
import io.vavr.control.Try;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface SecurityService {
    Try<Authentication> generateAuthentication(@Valid @NotNull AuthenticationRequest request);
}
