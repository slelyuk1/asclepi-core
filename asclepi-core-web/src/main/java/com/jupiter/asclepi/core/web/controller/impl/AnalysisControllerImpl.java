package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.model.request.disease.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.model.response.disease.AnalysisInfo;
import com.jupiter.asclepi.core.service.AnalysisService;
import com.jupiter.asclepi.core.service.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.VisitService;
import com.jupiter.asclepi.core.service.exception.AsclepiRuntimeException;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.web.controller.AnalysisController;
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
@RequestMapping("/api/v1/analysis")
public class AnalysisControllerImpl implements AnalysisController {


    private final DiseaseHistoryService diseaseHistoryService;
    private final VisitService visitService;
    private final AnalysisService analysisService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<?> create(@NotNull CreateAnalysisRequest createRequest) {
        Try<ResponseEntity<?>> creationTry = analysisService.create(createRequest).map(analysis -> {
            AnalysisInfo analysisInfo = conversionService.convert(analysis, AnalysisInfo.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(analysisInfo);
        });
        return creationTry
                .recover(NonExistentIdException.class, e -> ControllerUtils.notFoundResponse())
                .onFailure(e -> log.error("An error occurred during disease history creation: ", e))
                .getOrElseThrow(AsclepiRuntimeException::new);
    }

    @Override
    public ResponseEntity<?> edit(@NotNull EditAnalysisRequest editRequest) {
        Try<ResponseEntity<?>> editionTry = analysisService.edit(editRequest).map(edited -> {
            AnalysisInfo info = conversionService.convert(edited, AnalysisInfo.class);
            return ResponseEntity.ok().body(info);
        });
        return editionTry
                .recover(NonExistentIdException.class, e -> ControllerUtils.notFoundResponse())
                .onFailure(e -> log.error("An error occurred during disease history creation: ", e))
                .getOrElseThrow(AsclepiRuntimeException::new);
    }

    @Override
    public List<AnalysisInfo> getAll() {
        return analysisService.getAll().stream()
                .map(analysis -> conversionService.convert(analysis, AnalysisInfo.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<AnalysisInfo> getOne(@NotNull GetAnalysisRequest getRequest) {
        return analysisService.getOne(getRequest)
                .map(analysis -> conversionService.convert(analysis, AnalysisInfo.class))
                .map(ResponseEntity::ok)
                .orElse(ControllerUtils.notFoundResponse());
    }

    @Override
    public List<AnalysisInfo> getAnalysisForVisit(GetVisitRequest visitGetter) {
        return visitService.getOne(visitGetter)
                .map(analysisService::getForVisit)
                .map(analyses -> analyses.stream()
                .map(analysis -> conversionService.convert(analysis, AnalysisInfo.class))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public List<AnalysisInfo> getAnalysisForDiseaseHistory(GetDiseaseHistoryRequest diseaseHistoryGetter) {
        return diseaseHistoryService.getOne(diseaseHistoryGetter)
                .map(analysisService::getForDiseaseHistory)
                .map(analyses -> analyses.stream()
                        .map(analysis -> conversionService.convert(analysis, AnalysisInfo.class))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }


    @Override
    public ResponseEntity<?> delete(@NotNull GetAnalysisRequest deleteRequest) {
        boolean result = analysisService.delete(deleteRequest);
        return result ? ResponseEntity.ok().build() : ControllerUtils.notFoundResponse();
    }
}
