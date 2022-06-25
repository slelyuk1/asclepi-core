package com.jupiter.asclepi.core.service.impl.security;

import com.jupiter.asclepi.core.model.request.security.AuthenticationRequest;
import com.jupiter.asclepi.core.service.api.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication generateAuthentication(AuthenticationRequest request) {
        Authentication forAuthentication = new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());
        return authenticationManager.authenticate(forAuthentication);
    }
}
