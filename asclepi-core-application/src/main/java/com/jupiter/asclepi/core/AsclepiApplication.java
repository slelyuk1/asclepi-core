package com.jupiter.asclepi.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class AsclepiApplication {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        SpringApplication.run(AsclepiApplication.class, args);
    }
}
