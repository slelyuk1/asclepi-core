package com.jupiter.asclepi.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// todo disable controller-level validation
@SpringBootApplication
public class AsclepiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AsclepiApplication.class, args);
    }
}
