package com.jupiter.asclepi.core.web.filter;

import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@AllArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final ConversionService conversionService;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.nonNull(authToken)) {
            Token token = tokenService.verifyToken(authToken);
            Authentication authentication = conversionService.convert(token.getExtendedInformation(), Authentication.class);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // todo why is this commented
//        if (request.getCookies() != null) {
//            Arrays.stream(request.getCookies())
//                    .filter(cookie -> cookie.getName().equals(SecurityConfiguration.AUTHENTICATION_COOKIE_NAME))
//                    .findAny()
//                    .map(cookie -> tokenService.verifyToken(cookie.getValue()))
//                    .map(token -> conversionService.convert(token.getExtendedInformation(), Authentication.class))
//                    .ifPresent(SecurityContextHolder.getContext()::setAuthentication);
//        }
        filterChain.doFilter(request, response);
    }
}
