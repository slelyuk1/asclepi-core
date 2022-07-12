package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.VisitInfo;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.repository.entity.visit.VisitId;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.api.VisitService;
import com.jupiter.asclepi.core.service.helper.api.CreateService;
import com.jupiter.asclepi.core.service.helper.api.EditService;
import com.jupiter.asclepi.core.service.helper.api.GetAllService;
import com.jupiter.asclepi.core.service.helper.api.GetService;
import com.jupiter.asclepi.core.web.controller.VisitController;
import com.jupiter.asclepi.core.web.helper.api.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/visit")
public class VisitControllerImpl extends AbstractController<VisitInfo> implements VisitController {

    private final DiseaseHistoryService diseaseHistoryService;
    private final VisitService visitService;

    public VisitControllerImpl(ConversionService conversionService, DiseaseHistoryService diseaseHistoryService, VisitService visitService) {
        super(conversionService);
        this.diseaseHistoryService = diseaseHistoryService;
        this.visitService = visitService;
    }

    @Override
    public CreateService<CreateVisitRequest, Visit, VisitId> getServiceForCreation() {
        return visitService;
    }

    @Override
    public EditService<EditVisitRequest, Visit, VisitId> getServiceForPatch() {
        return visitService;
    }

    @Override
    public GetAllService<Visit, VisitId> getServiceForGetAll() {
        return visitService;
    }

    @Override
    public GetService<GetVisitRequest, Visit, VisitId> getServiceForGet() {
        return visitService;
    }

    @Override
    public List<VisitInfo> getForDiseaseHistory(@NotNull BigInteger clientId, @NotNull Integer diseaseHistoryNumber) {
        GetDiseaseHistoryRequest historyGetter = new GetDiseaseHistoryRequest(clientId, diseaseHistoryNumber);
        return diseaseHistoryService.getOne(historyGetter)
                .map(visitService::getForDiseaseHistory)
                .map(visits -> visits.stream()
                        .map(visit -> getConversionService().convert(visit, VisitInfo.class))
                        .toList())
                .orElse(Collections.emptyList());
    }

}
