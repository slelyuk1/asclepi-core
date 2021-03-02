package com.jupiter.asclepi.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.service.impl.employee.converter.CreateEmployeeRequestConverter;
import com.jupiter.asclepi.core.service.impl.employee.converter.EditEmployeeRequestConverter;
import com.jupiter.asclepi.core.service.impl.employee.converter.EmployeeConverter;
import com.jupiter.asclepi.core.service.impl.security.converter.AuthenticationToStringConverter;
import com.jupiter.asclepi.core.service.impl.security.converter.EmployeeToUserConverter;
import com.jupiter.asclepi.core.service.impl.security.converter.StringToAuthenticationConverter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AllArgsConstructor
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CreateEmployeeRequestConverter());
        registry.addConverter(new EditEmployeeRequestConverter());
        registry.addConverter(new EmployeeConverter());
        registry.addConverter(new EmployeeToUserConverter());
        registry.addConverter(new AuthenticationToStringConverter());
        registry.addConverter(new StringToAuthenticationConverter());
    }
}
