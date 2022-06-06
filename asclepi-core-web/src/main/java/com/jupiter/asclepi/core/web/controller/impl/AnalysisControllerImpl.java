package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.request.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.request.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.AnalysisInfo;
import com.jupiter.asclepi.core.repository.entity.analysis.Analysis;
import com.jupiter.asclepi.core.service.api.AnalysisService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.api.VisitService;
import com.jupiter.asclepi.core.web.controller.AnalysisController;
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
@RequestMapping("/api/v1/analysis")
public class AnalysisControllerImpl implements AnalysisController {


    private final DiseaseHistoryService diseaseHistoryService;
    private final VisitService visitService;
    private final AnalysisService analysisService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<AnalysisInfo> create(@NotNull CreateAnalysisRequest createRequest) {
        Analysis analysis = analysisService.create(createRequest);
        AnalysisInfo analysisInfo = conversionService.convert(analysis, AnalysisInfo.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(analysisInfo);
    }

    @Override
    public ResponseEntity<AnalysisInfo> edit(@NotNull EditAnalysisRequest editRequest) {
        Analysis edited = analysisService.edit(editRequest);
        AnalysisInfo info = conversionService.convert(edited, AnalysisInfo.class);
        return ResponseEntity.ok().body(info);
    }

    @Override
    public List<AnalysisInfo> getAll() {
        return analysisService.getAll().stream()
                .map(analysis -> conversionService.convert(analysis, AnalysisInfo.class))
                .toList();
    }

    @Override
    public ResponseEntity<AnalysisInfo> getOne(@NotNull GetAnalysisRequest getRequest) {
        return analysisService.getOne(getRequest)
                .map(analysis -> conversionService.convert(analysis, AnalysisInfo.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public List<AnalysisInfo> getAnalysisForVisit(GetVisitRequest visitGetter) {
        return visitService.getOne(visitGetter)
                .map(analysisService::getForVisit)
                .map(analyses -> analyses.stream()
                        .map(analysis -> conversionService.convert(analysis, AnalysisInfo.class))
                        .toList())
                .orElse(Collections.emptyList());
    }

    @Override
    public List<AnalysisInfo> getAnalysisForDiseaseHistory(GetDiseaseHistoryRequest diseaseHistoryGetter) {
        return diseaseHistoryService.getOne(diseaseHistoryGetter)
                .map(analysisService::getForDiseaseHistory)
                .map(analyses -> analyses.stream()
                        .map(analysis -> conversionService.convert(analysis, AnalysisInfo.class))
                        .toList())
                .orElse(Collections.emptyList());
    }


    @Override
    public ResponseEntity<Void> delete(@NotNull GetAnalysisRequest deleteRequest) {
        boolean result = analysisService.delete(deleteRequest);
        return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
