package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.DiagnosisInfo;
import com.jupiter.asclepi.core.repository.entity.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.service.api.DiagnosisService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.api.VisitService;
import com.jupiter.asclepi.core.web.controller.DiagnosisController;
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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/diagnosis")
public class DiagnosisControllerImpl implements DiagnosisController {


    private final DiseaseHistoryService diseaseHistoryService;
    private final VisitService visitService;
    private final DiagnosisService diagnosisService;
    private final ConversionService conversionService;


    @Override
    public ResponseEntity<DiagnosisInfo> create(@NotNull CreateDiagnosisRequest createRequest) {
        Diagnosis diagnosis = diagnosisService.create(createRequest);
        DiagnosisInfo diagnosisInfo = conversionService.convert(diagnosis, DiagnosisInfo.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(diagnosisInfo);
    }

    @Override
    public ResponseEntity<Void> delete(@NotNull GetDiagnosisRequest deleteRequest) {
        boolean result = diagnosisService.delete(deleteRequest);
        return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<DiagnosisInfo> edit(@NotNull EditDiagnosisRequest editRequest) {
        Diagnosis edited = diagnosisService.edit(editRequest);
        DiagnosisInfo info = conversionService.convert(edited, DiagnosisInfo.class);
        return ResponseEntity.ok().body(info);
    }

    @Override
    public ResponseEntity<DiagnosisInfo> getOne(@NotNull GetDiagnosisRequest getRequest) {
        return diagnosisService.getOne(getRequest)
                .map(diagnosis -> conversionService.convert(diagnosis, DiagnosisInfo.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public List<DiagnosisInfo> getForDiseaseHistory(GetDiseaseHistoryRequest request) {
        return diseaseHistoryService.getOne(request)
                .map(diagnosisService::getForDiseaseHistory)
                .map(diagnoses -> diagnoses.stream()
                        .map(diagnosis -> conversionService.convert(diagnosis, DiagnosisInfo.class))
                        .toList())
                .orElse(Collections.emptyList());
    }

    @Override
    public List<DiagnosisInfo> getAll() {
        return diagnosisService.getAll().stream()
                .map(diagnosis -> conversionService.convert(diagnosis, DiagnosisInfo.class))
                .toList();
    }
}
