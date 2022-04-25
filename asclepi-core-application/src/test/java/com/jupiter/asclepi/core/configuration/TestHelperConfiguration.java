package com.jupiter.asclepi.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.helper.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestHelperConfiguration {

    @Bean
    public EmployeeTestHelper employeeTestHelper(ObjectMapper objectMapper) {
        return new EmployeeTestHelper(objectMapper);
    }

    @Bean
    public ClientTestHelper clientTestHelper(ObjectMapper objectMapper) {
        return new ClientTestHelper(objectMapper);
    }

    @Bean
    public AnamnesisTestHelper anamnesisTestHelper(ObjectMapper objectMapper) {
        return new AnamnesisTestHelper(objectMapper);
    }

    @Bean
    public ConsultationTestHelper consultationTestHelper(ObjectMapper objectMapper) {
        return new ConsultationTestHelper(objectMapper);
    }

    @Bean
    public DiagnosisTestHelper diagnosisTestHelper(ObjectMapper objectMapper) {
        return new DiagnosisTestHelper(objectMapper);
    }

    @Bean
    public DiseaseHistoryTestHelper diseaseHistoryTestHelper(ObjectMapper objectMapper) {
        return new DiseaseHistoryTestHelper(objectMapper);
    }

    @Bean
    public VisitTestHelper visitTestHelper(DiseaseHistoryTestHelper diseaseHistoryTestHelper,
                                           ObjectMapper objectMapper) {
        return new VisitTestHelper(diseaseHistoryTestHelper, objectMapper);
    }

    @Bean
    public AnalysisTestHelper analysisTestHelper(ObjectMapper mapper){
        return new AnalysisTestHelper(mapper);
    }

    @Bean
    public SecurityTestHelper securityTestHelper(ObjectMapper mapper){
        return new SecurityTestHelper(mapper);
    }

}
