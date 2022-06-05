package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.request.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.model.request.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.ConsultationInfo;
import com.jupiter.asclepi.core.repository.entity.consultation.Consultation;
import com.jupiter.asclepi.core.service.api.ConsultationService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.api.VisitService;
import com.jupiter.asclepi.core.web.controller.ConsultationController;
import com.jupiter.asclepi.core.web.util.ControllerUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/consultation")
public class ConsultationControllerImpl implements ConsultationController {

    private final ConsultationService consultationService;
    private final DiseaseHistoryService historyService;
    private final VisitService visitService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<ConsultationInfo> create(@NotNull CreateConsultationRequest createRequest) {
        Consultation consultation = consultationService.create(createRequest);
        ConsultationInfo consultationInfo = conversionService.convert(consultation, ConsultationInfo.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(consultationInfo);
    }

    @Override
    public ResponseEntity<?> delete(@NotNull GetConsultationRequest deleteRequest) {
        boolean result = consultationService.delete(deleteRequest);
        return result ? ResponseEntity.ok().build() : ControllerUtils.notFoundResponse();
    }

    @Override
    public ResponseEntity<?> edit(@NotNull EditConsultationRequest editRequest) {
        Consultation edited = consultationService.edit(editRequest);
        ConsultationInfo consultationInfo = conversionService.convert(edited, ConsultationInfo.class);
        return ResponseEntity.ok().body(consultationInfo);
    }

    @Override
    public List<ConsultationInfo> getAll() {
        return consultationService.getAll().stream()
                .map(consultation -> Objects.requireNonNull(conversionService.convert(consultation, ConsultationInfo.class)))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ConsultationInfo> getOne(@NotNull GetConsultationRequest getRequest) {
        return consultationService.getOne(getRequest)
                .map(consultation -> conversionService.convert(consultation, ConsultationInfo.class))
                .map(ResponseEntity::ok)
                .orElse(ControllerUtils.notFoundResponse());
    }

    @Override
    public List<ConsultationInfo> getForVisit(@NotNull GetVisitRequest request) {
        return visitService.getOne(request)
                .map(visit -> consultationService.getForVisit(visit).stream()
                        .map(consultation -> conversionService.convert(consultation, ConsultationInfo.class))
                        .collect(Collectors.toList())
                ).orElse(Collections.emptyList());
    }

    @Override
    public List<ConsultationInfo> getForDiseaseHistory(@NotNull GetDiseaseHistoryRequest request) {
        return historyService.getOne(request)
                .map(history ->
                        consultationService.getForDiseaseHistory(history).stream()
                                .map(consultation -> conversionService.convert(consultation, ConsultationInfo.class))
                                .collect(Collectors.toList())
                ).orElse(Collections.emptyList());
    }
}
