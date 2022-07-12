package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.request.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.model.request.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.ConsultationInfo;
import com.jupiter.asclepi.core.repository.entity.consultation.Consultation;
import com.jupiter.asclepi.core.repository.entity.consultation.ConsultationId;
import com.jupiter.asclepi.core.service.api.ConsultationService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.api.VisitService;
import com.jupiter.asclepi.core.service.helper.api.CrudService;
import com.jupiter.asclepi.core.web.controller.ConsultationController;
import com.jupiter.asclepi.core.web.helper.api.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/consultation")
public class ConsultationControllerImpl extends AbstractController<ConsultationInfo> implements ConsultationController {

    private final ConsultationService consultationService;
    private final DiseaseHistoryService historyService;
    private final VisitService visitService;

    public ConsultationControllerImpl(ConversionService conversionService, ConsultationService consultationService, DiseaseHistoryService historyService, VisitService visitService) {
        super(conversionService);
        this.consultationService = consultationService;
        this.historyService = historyService;
        this.visitService = visitService;
    }

    @Override
    public CrudService<GetConsultationRequest, CreateConsultationRequest, EditConsultationRequest, Consultation, ConsultationId> getCrudService() {
        return consultationService;
    }

    @Override
    public List<ConsultationInfo> getForVisit(@NotNull GetVisitRequest request) {
        return visitService.getOne(request)
                .map(visit -> consultationService.getForVisit(visit).stream()
                        .map(consultation -> getConversionService().convert(consultation, ConsultationInfo.class))
                        .toList()
                ).orElse(Collections.emptyList());
    }

    @Override
    public List<ConsultationInfo> getForDiseaseHistory(@NotNull GetDiseaseHistoryRequest request) {
        return historyService.getOne(request)
                .map(history ->
                        consultationService.getForDiseaseHistory(history).stream()
                                .map(consultation -> getConversionService().convert(consultation, ConsultationInfo.class))
                                .toList()
                ).orElse(Collections.emptyList());
    }

}
