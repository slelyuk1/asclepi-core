package com.jupiter.asclepi.core.service.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public final class SecurityUtils {

    public static Optional<Authentication> getCurrentAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<T> getPrincipal(Class<T> principalClass) {
        return getCurrentAuthentication()
                .map(Authentication::getPrincipal)
                .map(principal -> {
                    if (principalClass.isAssignableFrom(principal.getClass())) {
                        return (T) principal;
                    }
                    // todo normal exception
                    throw new IllegalArgumentException("Actual principal is not instance of requested class");
                });
    }

    private SecurityUtils() {
        throw new IllegalStateException(getClass() + " is not expected to be instantiated");
    }
}
