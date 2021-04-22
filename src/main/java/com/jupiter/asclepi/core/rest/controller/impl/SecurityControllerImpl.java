package com.jupiter.asclepi.core.rest.controller.impl;

import com.jupiter.asclepi.core.configuration.SecurityConfiguration;
import com.jupiter.asclepi.core.exception.AsclepiRuntimeException;
import com.jupiter.asclepi.core.model.request.other.AuthenticationRequest;
import com.jupiter.asclepi.core.model.response.error.ErrorInfo;
import com.jupiter.asclepi.core.rest.controller.SecurityController;
import com.jupiter.asclepi.core.service.SecurityService;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(SecurityConfiguration.AUTHENTICATION_ENDPOINT_PREFIX)
public class SecurityControllerImpl implements SecurityController {
    private static final String AUTHENTICATION_ERROR_MESSAGE = "Cannot authenticate. Invalid credentials.";

    private final SecurityService securityService;
    private final TokenService tokenService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<?> authenticate(AuthenticationRequest request) {
        Try<ResponseEntity<?>> authenticationTry = securityService.generateAuthentication(request)
                .mapTry(authentication -> {
                    String serializedAuthentication = conversionService.convert(authentication, String.class);
                    Token token = tokenService.allocateToken(serializedAuthentication);
                    return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token.getKey()).build();
                });
        return authenticationTry
                .recover(AuthenticationException.class, e -> {
                    log.warn("AuthenticationException occurred:", e);
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorInfo(AUTHENTICATION_ERROR_MESSAGE));
                })
                .onFailure(e -> log.error("An exception occurred during authentication:", e))
                .getOrElseThrow(AsclepiRuntimeException::new);
    }

    private static String tokenToAuthenticationCookie(Token token) {
        return String.format("%s=%s; SameSite=None; Secured", SecurityConfiguration.AUTHENTICATION_COOKIE_NAME, token.getKey());
    }
}
