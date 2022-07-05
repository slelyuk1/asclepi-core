package com.jupiter.asclepi.core.repository.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "loginAuditorAware")
@Configuration
class RepositoryConfiguration {
}
