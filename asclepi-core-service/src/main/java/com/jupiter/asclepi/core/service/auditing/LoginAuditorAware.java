package com.jupiter.asclepi.core.service.auditing;

import com.jupiter.asclepi.core.service.util.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("loginAuditorAware")
public class LoginAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return SecurityUtils.getPrincipal(UserDetails.class).map(UserDetails::getUsername);
    }

}
