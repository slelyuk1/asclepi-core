package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.request.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.request.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.AnalysisInfo;
import com.jupiter.asclepi.core.repository.entity.analysis.Analysis;
import com.jupiter.asclepi.core.repository.entity.analysis.AnalysisId;
import com.jupiter.asclepi.core.service.api.AnalysisService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.api.VisitService;
import com.jupiter.asclepi.core.service.helper.api.CrudService;
import com.jupiter.asclepi.core.web.controller.AnalysisController;
import com.jupiter.asclepi.core.web.helper.api.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/analysis")
public class AnalysisControllerImpl extends AbstractController<AnalysisInfo> implements AnalysisController {

    private final DiseaseHistoryService diseaseHistoryService;
    private final VisitService visitService;
    private final AnalysisService analysisService;

    public AnalysisControllerImpl(ConversionService conversionService, DiseaseHistoryService diseaseHistoryService, VisitService visitService, AnalysisService analysisService) {
        super(conversionService);
        this.diseaseHistoryService = diseaseHistoryService;
        this.visitService = visitService;
        this.analysisService = analysisService;
    }

    @Override
    public CrudService<GetAnalysisRequest, CreateAnalysisRequest, EditAnalysisRequest, Analysis, AnalysisId> getCrudService() {
        return analysisService;
    }

    @Override
    public List<AnalysisInfo> getAnalysisForVisit(GetVisitRequest visitGetter) {
        return visitService.getOne(visitGetter)
                .map(analysisService::getForVisit)
                .map(analyses -> analyses.stream()
                        .map(analysis -> getConversionService().convert(analysis, AnalysisInfo.class))
                        .toList())
                .orElse(Collections.emptyList());
    }

    @Override
    public List<AnalysisInfo> getAnalysisForDiseaseHistory(GetDiseaseHistoryRequest diseaseHistoryGetter) {
        return diseaseHistoryService.getOne(diseaseHistoryGetter)
                .map(analysisService::getForDiseaseHistory)
                .map(analyses -> analyses.stream()
                        .map(analysis -> getConversionService().convert(analysis, AnalysisInfo.class))
                        .toList())
                .orElse(Collections.emptyList());
    }

}
