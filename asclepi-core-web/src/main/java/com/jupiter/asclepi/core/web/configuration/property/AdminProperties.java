package com.jupiter.asclepi.core.web.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.admin")
public record AdminProperties(String login, String password, String name, String surname) {
}
