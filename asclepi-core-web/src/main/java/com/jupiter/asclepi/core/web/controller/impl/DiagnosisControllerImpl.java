package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.response.disease.DiagnosisInfo;
import com.jupiter.asclepi.core.service.api.DiagnosisService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.api.VisitService;
import com.jupiter.asclepi.core.service.exception.AsclepiRuntimeException;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.web.controller.DiagnosisController;
import com.jupiter.asclepi.core.web.util.ControllerUtils;
import io.vavr.control.Try;
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
import java.util.stream.Collectors;

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
    public ResponseEntity<?> create(@NotNull CreateDiagnosisRequest createRequest) {
        Try<ResponseEntity<?>> creationTry = diagnosisService.create(createRequest).map(diagnosis -> {
            DiagnosisInfo diagnosisInfo = conversionService.convert(diagnosis, DiagnosisInfo.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(diagnosisInfo);
        });
        return creationTry
                .recover(NonExistentIdException.class, e -> ControllerUtils.notFoundResponse())
                .onFailure(e -> log.error("An error occurred during disease history creation: ", e))
                .getOrElseThrow(AsclepiRuntimeException::new);
    }

    @Override
    public ResponseEntity<?> delete(@NotNull GetDiagnosisRequest deleteRequest) {
        boolean result = diagnosisService.delete(deleteRequest);
        return result ? ResponseEntity.ok().build() : ControllerUtils.notFoundResponse();
    }

    @Override
    public ResponseEntity<?> edit(@NotNull EditDiagnosisRequest editRequest) {
        Try<ResponseEntity<?>> editionTry = diagnosisService.edit(editRequest).map(edited -> {
            DiagnosisInfo info = conversionService.convert(edited, DiagnosisInfo.class);
            return ResponseEntity.ok().body(info);
        });
        return editionTry
                .recover(NonExistentIdException.class, e -> ControllerUtils.notFoundResponse())
                .onFailure(e -> log.error("An error occurred during disease history creation: ", e))
                .getOrElseThrow(AsclepiRuntimeException::new);
    }

    @Override
    public ResponseEntity<DiagnosisInfo> getOne(@NotNull GetDiagnosisRequest getRequest) {
        return diagnosisService.getOne(getRequest)
                .map(diagnosis -> conversionService.convert(diagnosis, DiagnosisInfo.class))
                .map(ResponseEntity::ok)
                .orElse(ControllerUtils.notFoundResponse());
    }

    @Override
    public List<DiagnosisInfo> getForDiseaseHistory(GetDiseaseHistoryRequest request) {
        return diseaseHistoryService.getOne(request)
                .map(diagnosisService::getForDiseaseHistory)
                .map(diagnoses -> diagnoses.stream()
                        .map(diagnosis -> conversionService.convert(diagnosis, DiagnosisInfo.class))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public List<DiagnosisInfo> getAll() {
        return diagnosisService.getAll().stream()
                .map(diagnosis -> conversionService.convert(diagnosis, DiagnosisInfo.class))
                .collect(Collectors.toList());
    }
}
