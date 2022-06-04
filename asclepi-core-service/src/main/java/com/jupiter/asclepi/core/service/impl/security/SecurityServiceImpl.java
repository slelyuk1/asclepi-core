package com.jupiter.asclepi.core.service.impl.security;

import com.jupiter.asclepi.core.model.request.security.AuthenticationRequest;
import com.jupiter.asclepi.core.service.api.SecurityService;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class SecurityServiceImpl implements SecurityService {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public SecurityServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Try<Authentication> generateAuthentication(AuthenticationRequest request) {
        Authentication forAuthentication = new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());
        return Try.of(() -> authenticationManager.authenticate(forAuthentication));
    }
}
