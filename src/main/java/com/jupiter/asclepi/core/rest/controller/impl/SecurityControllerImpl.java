package com.jupiter.asclepi.core.rest.controller.impl;

import ch.qos.logback.core.net.ObjectWriter;
import com.jupiter.asclepi.core.configuration.SecurityConfiguration;
import com.jupiter.asclepi.core.exception.AsclepiRuntimeException;
import com.jupiter.asclepi.core.model.request.other.AuthenticationRequest;
import com.jupiter.asclepi.core.rest.controller.SecurityController;
import com.jupiter.asclepi.core.rest.controller.util.ControllerUtils;
import com.jupiter.asclepi.core.service.SecurityService;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(SecurityConfiguration.AUTHENTICATION_ENDPOINT_PREFIX)
public class SecurityControllerImpl implements SecurityController {
    private final SecurityService securityService;
    private final TokenService tokenService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<?> authenticate(AuthenticationRequest request) {
        Try<ResponseEntity<?>> authenticationTry = securityService.generateAuthentication(request)
                .mapTry(authentication -> conversionService.convert(authentication, String.class))
                .mapTry(tokenService::allocateToken)
                .map(SecurityControllerImpl::tokenToAuthenticationCookie)
                .map(cookieValue -> ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookieValue).build());
        return authenticationTry
                .recover(UsernameNotFoundException.class, ControllerUtils.notFoundResponse())
                .recover(BadCredentialsException.class, ResponseEntity.status(HttpStatus.FORBIDDEN).build())
                .onFailure(e -> log.error("An exception occurred during authentication:", e))
                .getOrElseThrow(AsclepiRuntimeException::new);
    }

    private static String tokenToAuthenticationCookie(Token token){
        return String.format("%s=%s", SecurityConfiguration.AUTHENTICATION_COOKIE_NAME, token.getKey());
    }
}
