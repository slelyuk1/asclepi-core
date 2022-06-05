package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.model.request.security.AuthenticationRequest;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface SecurityService {
    Authentication generateAuthentication(@Valid @NotNull AuthenticationRequest request);
}
