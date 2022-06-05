package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.security.AuthenticationRequest;
import com.jupiter.asclepi.core.service.api.SecurityService;
import com.jupiter.asclepi.core.web.configuration.WebSecurityConfiguration;
import com.jupiter.asclepi.core.web.controller.SecurityController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(WebSecurityConfiguration.AUTHENTICATION_ENDPOINT_PREFIX)
public class SecurityControllerImpl implements SecurityController {

    private final SecurityService securityService;
    private final TokenService tokenService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<Void> authenticate(AuthenticationRequest request) {
        Authentication authentication = securityService.generateAuthentication(request);
        String serializedAuthentication = conversionService.convert(authentication, String.class);
        Token token = tokenService.allocateToken(serializedAuthentication);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token.getKey())
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION)
                .build();
    }

    // todo why is not used
    // private static String tokenToAuthenticationCookie(Token token) {
    //     return String.format("%s=%s; SameSite=None; Secured", WebSecurityConfiguration.AUTHENTICATION_COOKIE_NAME, token.getKey());
    // }
}
