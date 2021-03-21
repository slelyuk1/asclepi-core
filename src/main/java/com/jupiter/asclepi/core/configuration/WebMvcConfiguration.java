package com.jupiter.asclepi.core.configuration;

import com.jupiter.asclepi.core.service.impl.anamnesis.converter.CreateAnamnesisRequestConverter;
import com.jupiter.asclepi.core.service.impl.client.converter.ClientConverter;
import com.jupiter.asclepi.core.service.impl.client.converter.CreateClientRequestConverter;
import com.jupiter.asclepi.core.service.impl.client.converter.EditClientRequestConverter;
import com.jupiter.asclepi.core.service.impl.diseaseHistory.converter.CreateDiseaseHistoryConverter;
import com.jupiter.asclepi.core.service.impl.diseaseHistory.converter.DiseaseHistoryConverter;
import com.jupiter.asclepi.core.service.impl.diseaseHistory.converter.EditDiseaseHistoryConverter;
import com.jupiter.asclepi.core.service.impl.diseaseHistory.converter.GetDiseaseHistoryRequestConverter;
import com.jupiter.asclepi.core.service.impl.employee.converter.CreateEmployeeRequestConverter;
import com.jupiter.asclepi.core.service.impl.employee.converter.EditEmployeeRequestConverter;
import com.jupiter.asclepi.core.service.impl.employee.converter.EmployeeConverter;
import com.jupiter.asclepi.core.service.impl.security.converter.AuthenticationToStringConverter;
import com.jupiter.asclepi.core.service.impl.security.converter.EmployeeToUserConverter;
import com.jupiter.asclepi.core.service.impl.security.converter.StringToAuthenticationConverter;
import com.jupiter.asclepi.core.service.impl.visit.converter.CreateVisitRequestConverter;
import com.jupiter.asclepi.core.service.impl.visit.converter.EditVisitRequestConverter;
import com.jupiter.asclepi.core.service.impl.visit.converter.GetVisitRequestConverter;
import com.jupiter.asclepi.core.service.impl.visit.converter.VisitConverter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AllArgsConstructor
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

        registry.addConverter(new EmployeeToUserConverter());
        registry.addConverter(new AuthenticationToStringConverter());
        registry.addConverter(new StringToAuthenticationConverter());

        registry.addConverter(new DiseaseHistoryConverter());
        registry.addConverter(new CreateDiseaseHistoryConverter());
        registry.addConverter(new EditDiseaseHistoryConverter());
        registry.addConverter(new GetDiseaseHistoryRequestConverter());

        registry.addConverter(new CreateVisitRequestConverter());
        registry.addConverter(new EditVisitRequestConverter());
        registry.addConverter(new GetVisitRequestConverter());
        registry.addConverter(new VisitConverter());

        registry.addConverter(new CreateAnamnesisRequestConverter());
    }
}
