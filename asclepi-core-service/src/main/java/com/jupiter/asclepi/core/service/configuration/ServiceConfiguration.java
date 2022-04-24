package com.jupiter.asclepi.core.service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
public class ServiceConfiguration {

    @Bean
    public Base64.Encoder encoderForBytes() {
        return Base64.getEncoder();
    }

    @Bean
    public Base64.Decoder decoderForBytes() {
        return Base64.getDecoder();
    }

}
