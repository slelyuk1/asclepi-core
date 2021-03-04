package com.jupiter.asclepi.core.rest.filter;

import com.jupiter.asclepi.core.configuration.SecurityConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@AllArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final ConversionService conversionService;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getCookies() != null) {
            Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(SecurityConfiguration.AUTHENTICATION_COOKIE_NAME))
                    .findAny()
                    .map(cookie -> tokenService.verifyToken(cookie.getValue()))
                    .map(token -> conversionService.convert(token.getExtendedInformation(), Authentication.class))
                    .ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        }
        filterChain.doFilter(request, response);
    }
}
