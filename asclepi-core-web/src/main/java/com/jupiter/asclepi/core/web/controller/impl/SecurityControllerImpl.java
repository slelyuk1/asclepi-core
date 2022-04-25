package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.model.request.other.AuthenticationRequest;
import com.jupiter.asclepi.core.model.model.response.error.ErrorInfo;
import com.jupiter.asclepi.core.service.api.SecurityService;
import com.jupiter.asclepi.core.service.exception.AsclepiRuntimeException;
import com.jupiter.asclepi.core.web.configuration.WebSecurityConfiguration;
import com.jupiter.asclepi.core.web.controller.SecurityController;
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
@RequestMapping(WebSecurityConfiguration.AUTHENTICATION_ENDPOINT_PREFIX)
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
                    return ResponseEntity.ok()
                            .header(HttpHeaders.AUTHORIZATION, token.getKey())
                            .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION)
                            .build();
                });
        return authenticationTry
                .recover(AuthenticationException.class, e -> {
                    log.warn("AuthenticationException occurred:", e);
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorInfo(AUTHENTICATION_ERROR_MESSAGE));
                })
                .onFailure(e -> log.error("An exception occurred during authentication:", e))
                .getOrElseThrow(AsclepiRuntimeException::new);
    }

    // todo why is not used
    // private static String tokenToAuthenticationCookie(Token token) {
    //     return String.format("%s=%s; SameSite=None; Secured", WebSecurityConfiguration.AUTHENTICATION_COOKIE_NAME, token.getKey());
    // }
}
