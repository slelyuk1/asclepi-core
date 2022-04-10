package com.jupiter.asclepi.core.web.configuration;

import com.jupiter.asclepi.core.model.model.entity.disease.consultation.ConsultationId;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.VisitId;
import com.jupiter.asclepi.core.model.model.request.disease.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.service.converter.analysis.AnalysisConverter;
import com.jupiter.asclepi.core.service.converter.analysis.CreateAnalysisRequestConverter;
import com.jupiter.asclepi.core.service.converter.analysis.EditAnalysisRequestConverter;
import com.jupiter.asclepi.core.service.converter.analysis.GetAnalysisRequestConverter;
import com.jupiter.asclepi.core.service.converter.anamnesis.AnamnesisConverter;
import com.jupiter.asclepi.core.service.converter.anamnesis.CreateAnamnesisRequestConverter;
import com.jupiter.asclepi.core.service.converter.client.ClientConverter;
import com.jupiter.asclepi.core.service.converter.client.CreateClientRequestConverter;
import com.jupiter.asclepi.core.service.converter.client.EditClientRequestConverter;
import com.jupiter.asclepi.core.service.converter.consultation.ConsultationConverter;
import com.jupiter.asclepi.core.service.converter.consultation.CreateConsultationRequestConverter;
import com.jupiter.asclepi.core.service.converter.consultation.EditConsultationRequestConverter;
import com.jupiter.asclepi.core.service.converter.consultation.GetConsultationRequestConverter;
import com.jupiter.asclepi.core.service.converter.diagnosis.CreateDiagnosisRequestConverter;
import com.jupiter.asclepi.core.service.converter.diagnosis.DiagnosisConverter;
import com.jupiter.asclepi.core.service.converter.diagnosis.EditDiagnosisRequestConverter;
import com.jupiter.asclepi.core.service.converter.diagnosis.GetDiagnosisRequestConverter;
import com.jupiter.asclepi.core.service.converter.diseaseHistory.CreateDiseaseHistoryConverter;
import com.jupiter.asclepi.core.service.converter.diseaseHistory.DiseaseHistoryConverter;
import com.jupiter.asclepi.core.service.converter.diseaseHistory.EditDiseaseHistoryConverter;
import com.jupiter.asclepi.core.service.converter.diseaseHistory.GetDiseaseHistoryRequestConverter;
import com.jupiter.asclepi.core.service.converter.employee.CreateEmployeeRequestConverter;
import com.jupiter.asclepi.core.service.converter.employee.EditEmployeeRequestConverter;
import com.jupiter.asclepi.core.service.converter.employee.EmployeeConverter;
import com.jupiter.asclepi.core.service.converter.security.AuthenticationToStringConverter;
import com.jupiter.asclepi.core.service.converter.security.EmployeeToUserConverter;
import com.jupiter.asclepi.core.service.converter.security.StringToAuthenticationConverter;
import com.jupiter.asclepi.core.service.converter.visit.CreateVisitRequestConverter;
import com.jupiter.asclepi.core.service.converter.visit.EditVisitRequestConverter;
import com.jupiter.asclepi.core.service.converter.visit.GetVisitRequestConverter;
import com.jupiter.asclepi.core.service.converter.visit.VisitConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Base64;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .exposedHeaders("*")
//                .allowCredentials(true)
//                .maxAge(3600);
//
////        CorsConfiguration configuration = new CorsConfiguration();
//////        configuration.setAllowedOrigins(Collections.singletonList("https://asclepi-web-client.herokuapp.com"));
////        configuration.addAllowedOrigin("*");  // TODO: lock down before deploying
////        configuration.addAllowedHeader("*");
////        configuration.addExposedHeader("*");
////        configuration.addAllowedMethod("*");
////        configuration.setAllowCredentials(true);
////
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", configuration);
////        return source;
//    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CreateEmployeeRequestConverter());
        registry.addConverter(new EditEmployeeRequestConverter());
        registry.addConverter(new EmployeeConverter());

        registry.addConverter(new CreateClientRequestConverter());
        registry.addConverter(new EditClientRequestConverter());
        registry.addConverter(new ClientConverter());

        registry.addConverter(new EmployeeToUserConverter());
        registry.addConverter(new AuthenticationToStringConverter(encoderForBytes()));
        registry.addConverter(new StringToAuthenticationConverter(decoderForBytes()));

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

        registry.addConverter(new CreateDiagnosisRequestConverter(historyIdConverter));
        registry.addConverter(new EditDiagnosisRequestConverter(historyIdConverter));
        registry.addConverter(new GetDiagnosisRequestConverter(historyIdConverter));
        registry.addConverter(new DiagnosisConverter());

        registry.addConverter(new CreateAnamnesisRequestConverter());
        registry.addConverter(new AnamnesisConverter());

        Converter<GetConsultationRequest, ConsultationId> consultationIdConverter = new GetConsultationRequestConverter(visitIdConverter);
        registry.addConverter(new CreateConsultationRequestConverter());
        registry.addConverter(new EditConsultationRequestConverter(consultationIdConverter));
        registry.addConverter(consultationIdConverter);
        registry.addConverter(new ConsultationConverter());

        registry.addConverter(new CreateAnalysisRequestConverter(historyIdConverter, visitIdConverter));
        registry.addConverter(new EditAnalysisRequestConverter(historyIdConverter, visitIdConverter));
        registry.addConverter(new GetAnalysisRequestConverter(historyIdConverter, visitIdConverter));
        registry.addConverter(new AnalysisConverter());
    }

    @Bean
    public Base64.Encoder encoderForBytes() {
        return Base64.getEncoder();
    }

    @Bean
    public Base64.Decoder decoderForBytes() {
        return Base64.getDecoder();
    }
}
