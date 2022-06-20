package com.jupiter.asclepi.core.web.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security")
public record SecurityProperties(String secret, Integer serverInteger) {
}
