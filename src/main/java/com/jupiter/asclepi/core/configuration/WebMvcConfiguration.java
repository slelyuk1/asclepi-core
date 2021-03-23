package com.jupiter.asclepi.core.configuration;

import com.jupiter.asclepi.core.model.entity.disease.consultation.ConsultationId;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.entity.disease.visit.VisitId;
import com.jupiter.asclepi.core.model.request.disease.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.service.impl.anamnesis.converter.AnamnesisConverter;
import com.jupiter.asclepi.core.service.impl.anamnesis.converter.CreateAnamnesisRequestConverter;
import com.jupiter.asclepi.core.service.impl.client.converter.ClientConverter;
import com.jupiter.asclepi.core.service.impl.client.converter.CreateClientRequestConverter;
import com.jupiter.asclepi.core.service.impl.client.converter.EditClientRequestConverter;
import com.jupiter.asclepi.core.service.impl.consultation.converter.CreateConsultationRequestConverter;
import com.jupiter.asclepi.core.service.impl.consultation.converter.EditConsultationRequestConverter;
import com.jupiter.asclepi.core.service.impl.consultation.converter.GetConsultationRequestConverter;
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
import org.springframework.core.convert.converter.Converter;
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

        Converter<GetDiseaseHistoryRequest, DiseaseHistoryId> historyIdConverter = new GetDiseaseHistoryRequestConverter();
        registry.addConverter(new DiseaseHistoryConverter());
        registry.addConverter(new CreateDiseaseHistoryConverter());
        registry.addConverter(new EditDiseaseHistoryConverter());
        registry.addConverter(historyIdConverter);

        Converter<GetVisitRequest, VisitId> visitIdConverter = new GetVisitRequestConverter(historyIdConverter);
        registry.addConverter(new CreateVisitRequestConverter(historyIdConverter));
        registry.addConverter(new EditVisitRequestConverter());
        registry.addConverter(visitIdConverter);
        registry.addConverter(new VisitConverter());

        registry.addConverter(new CreateAnamnesisRequestConverter());
        registry.addConverter(new AnamnesisConverter());

        Converter<GetConsultationRequest, ConsultationId> consultationIdConverter = new GetConsultationRequestConverter(visitIdConverter);
        registry.addConverter(new CreateConsultationRequestConverter());
        registry.addConverter(new EditConsultationRequestConverter(consultationIdConverter));
        registry.addConverter(consultationIdConverter);
    }
}
