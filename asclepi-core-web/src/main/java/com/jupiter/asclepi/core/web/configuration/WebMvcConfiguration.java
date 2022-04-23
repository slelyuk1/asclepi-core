package com.jupiter.asclepi.core.web.configuration;

import com.jupiter.asclepi.core.model.model.entity.disease.consultation.ConsultationId;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.VisitId;
import com.jupiter.asclepi.core.model.model.request.disease.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.service.converter.analysis.LegacyAnalysisConverter;
import com.jupiter.asclepi.core.service.converter.analysis.LegacyCreateAnalysisRequestConverter;
import com.jupiter.asclepi.core.service.converter.analysis.LegacyEditAnalysisRequestConverter;
import com.jupiter.asclepi.core.service.converter.analysis.LegacyGetAnalysisRequestConverter;
import com.jupiter.asclepi.core.service.converter.anamnesis.LegacyAnamnesisConverter;
import com.jupiter.asclepi.core.service.converter.anamnesis.LegacyCreateAnamnesisRequestConverter;
import com.jupiter.asclepi.core.service.converter.client.LegacyClientConverter;
import com.jupiter.asclepi.core.service.converter.client.LegacyCreateClientRequestConverter;
import com.jupiter.asclepi.core.service.converter.client.LegacyEditClientRequestConverter;
import com.jupiter.asclepi.core.service.converter.consultation.LegacyConsultationConverter;
import com.jupiter.asclepi.core.service.converter.consultation.LegacyCreateConsultationRequestConverter;
import com.jupiter.asclepi.core.service.converter.consultation.LegacyEditConsultationRequestConverter;
import com.jupiter.asclepi.core.service.converter.consultation.LegacyGetConsultationRequestConverter;
import com.jupiter.asclepi.core.service.converter.diagnosis.LegacyCreateDiagnosisRequestConverter;
import com.jupiter.asclepi.core.service.converter.diagnosis.LegacyDiagnosisConverter;
import com.jupiter.asclepi.core.service.converter.diagnosis.LegacyEditDiagnosisRequestConverter;
import com.jupiter.asclepi.core.service.converter.diagnosis.LegacyGetDiagnosisRequestConverter;
import com.jupiter.asclepi.core.service.converter.diseaseHistory.LegacyCreateDiseaseHistoryConverter;
import com.jupiter.asclepi.core.service.converter.diseaseHistory.LegacyDiseaseHistoryConverter;
import com.jupiter.asclepi.core.service.converter.diseaseHistory.LegacyEditDiseaseHistoryConverter;
import com.jupiter.asclepi.core.service.converter.diseaseHistory.LegacyGetDiseaseHistoryRequestConverter;
import com.jupiter.asclepi.core.service.converter.employee.LegacyCreateEmployeeRequestConverter;
import com.jupiter.asclepi.core.service.converter.employee.LegacyEditEmployeeRequestConverter;
import com.jupiter.asclepi.core.service.converter.employee.LegacyEmployeeConverter;
import com.jupiter.asclepi.core.service.converter.security.AuthenticationToStringConverter;
import com.jupiter.asclepi.core.service.converter.security.EmployeeToUserConverter;
import com.jupiter.asclepi.core.service.converter.security.StringToAuthenticationConverter;
import com.jupiter.asclepi.core.service.converter.visit.LegacyCreateVisitRequestConverter;
import com.jupiter.asclepi.core.service.converter.visit.LegacyEditVisitRequestConverter;
import com.jupiter.asclepi.core.service.converter.visit.LegacyGetVisitRequestConverter;
import com.jupiter.asclepi.core.service.converter.visit.LegacyVisitConverter;
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
        registry.addConverter(new LegacyCreateEmployeeRequestConverter());
        registry.addConverter(new LegacyEditEmployeeRequestConverter());
        registry.addConverter(new LegacyEmployeeConverter());

        registry.addConverter(new LegacyCreateClientRequestConverter());
        registry.addConverter(new LegacyEditClientRequestConverter());
        registry.addConverter(new LegacyClientConverter());

        registry.addConverter(new EmployeeToUserConverter());
        registry.addConverter(new AuthenticationToStringConverter(encoderForBytes()));
        registry.addConverter(new StringToAuthenticationConverter(decoderForBytes()));

        Converter<GetDiseaseHistoryRequest, DiseaseHistoryId> historyIdConverter = new LegacyGetDiseaseHistoryRequestConverter();

        registry.addConverter(new LegacyDiseaseHistoryConverter());
        registry.addConverter(new LegacyCreateDiseaseHistoryConverter());
        registry.addConverter(new LegacyEditDiseaseHistoryConverter());
        registry.addConverter(historyIdConverter);

        Converter<GetVisitRequest, VisitId> visitIdConverter = new LegacyGetVisitRequestConverter(historyIdConverter);
        registry.addConverter(new LegacyCreateVisitRequestConverter(historyIdConverter));
        registry.addConverter(new LegacyEditVisitRequestConverter());
        registry.addConverter(visitIdConverter);
        registry.addConverter(new LegacyVisitConverter());

        registry.addConverter(new LegacyCreateDiagnosisRequestConverter(historyIdConverter));
        registry.addConverter(new LegacyEditDiagnosisRequestConverter(historyIdConverter));
        registry.addConverter(new LegacyGetDiagnosisRequestConverter(historyIdConverter));
        registry.addConverter(new LegacyDiagnosisConverter());

        registry.addConverter(new LegacyCreateAnamnesisRequestConverter());
        registry.addConverter(new LegacyAnamnesisConverter());

        Converter<GetConsultationRequest, ConsultationId> consultationIdConverter = new LegacyGetConsultationRequestConverter(visitIdConverter);
        registry.addConverter(new LegacyCreateConsultationRequestConverter());
        registry.addConverter(new LegacyEditConsultationRequestConverter(consultationIdConverter));
        registry.addConverter(consultationIdConverter);
        registry.addConverter(new LegacyConsultationConverter());

        registry.addConverter(new LegacyCreateAnalysisRequestConverter(historyIdConverter, visitIdConverter));
        registry.addConverter(new LegacyEditAnalysisRequestConverter(historyIdConverter, visitIdConverter));
        registry.addConverter(new LegacyGetAnalysisRequestConverter(historyIdConverter, visitIdConverter));
        registry.addConverter(new LegacyAnalysisConverter());
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
