package com.jupiter.asclepi.core.configuration;

import com.jupiter.asclepi.core.service.impl.client.converter.ClientConverter;
import com.jupiter.asclepi.core.service.impl.client.converter.CreateClientRequestConverter;
import com.jupiter.asclepi.core.service.impl.client.converter.EditClientRequestConverter;
import com.jupiter.asclepi.core.service.impl.employee.converter.CreateEmployeeRequestConverter;
import com.jupiter.asclepi.core.service.impl.employee.converter.EditEmployeeRequestConverter;
import com.jupiter.asclepi.core.service.impl.employee.converter.EmployeeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CreateEmployeeRequestConverter());
        registry.addConverter(new EditEmployeeRequestConverter());
        registry.addConverter(new EmployeeConverter());
        registry.addConverter(new CreateClientRequestConverter());
        registry.addConverter(new EditClientRequestConverter());
        registry.addConverter(new ClientConverter());
    }
}
