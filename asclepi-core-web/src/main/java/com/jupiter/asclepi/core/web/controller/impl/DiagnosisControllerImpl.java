package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.DiagnosisInfo;
import com.jupiter.asclepi.core.repository.entity.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.repository.entity.diagnosis.DiagnosisId;
import com.jupiter.asclepi.core.service.api.DiagnosisService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.helper.api.CrudService;
import com.jupiter.asclepi.core.web.controller.DiagnosisController;
import com.jupiter.asclepi.core.web.helper.api.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/diagnosis")
public class DiagnosisControllerImpl extends AbstractController<DiagnosisInfo> implements DiagnosisController {

    private final DiseaseHistoryService diseaseHistoryService;
    private final DiagnosisService diagnosisService;

    public DiagnosisControllerImpl(ConversionService conversionService, DiseaseHistoryService diseaseHistoryService, DiagnosisService diagnosisService, ConversionService conversionService1) {
        super(conversionService);
        this.diseaseHistoryService = diseaseHistoryService;
        this.diagnosisService = diagnosisService;
    }

    @Override
    public CrudService<GetDiagnosisRequest, CreateDiagnosisRequest, EditDiagnosisRequest, Diagnosis, DiagnosisId> getCrudService() {
        return diagnosisService;
    }

    @Override
    public List<DiagnosisInfo> getForDiseaseHistory(GetDiseaseHistoryRequest request) {
        return diseaseHistoryService.getOne(request)
                .map(diagnosisService::getForDiseaseHistory)
                .map(diagnoses -> diagnoses.stream()
                        .map(diagnosis -> getConversionService().convert(diagnosis, DiagnosisInfo.class))
                        .toList())
                .orElse(Collections.emptyList());
    }

}
