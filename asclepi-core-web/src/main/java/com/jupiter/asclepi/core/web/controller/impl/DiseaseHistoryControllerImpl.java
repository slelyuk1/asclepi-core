package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.DiseaseHistoryInfo;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistoryId;
import com.jupiter.asclepi.core.service.api.ClientService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.helper.api.CreateService;
import com.jupiter.asclepi.core.service.helper.api.EditService;
import com.jupiter.asclepi.core.service.helper.api.GetAllService;
import com.jupiter.asclepi.core.service.helper.api.GetService;
import com.jupiter.asclepi.core.web.controller.DiseaseHistoryController;
import com.jupiter.asclepi.core.web.helper.api.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/diseaseHistory")
public class DiseaseHistoryControllerImpl extends AbstractController<DiseaseHistoryInfo> implements DiseaseHistoryController {

    private final ClientService clientService;
    private final DiseaseHistoryService diseaseHistoryService;

    public DiseaseHistoryControllerImpl(ConversionService conversionService, ClientService clientService, DiseaseHistoryService diseaseHistoryService) {
        super(conversionService);
        this.clientService = clientService;
        this.diseaseHistoryService = diseaseHistoryService;
    }

    @Override
    public CreateService<CreateDiseaseHistoryRequest, DiseaseHistory, DiseaseHistoryId> getServiceForCreation() {
        return diseaseHistoryService;
    }

    @Override
    public EditService<EditDiseaseHistoryRequest, DiseaseHistory, DiseaseHistoryId> getServiceForPatch() {
        return diseaseHistoryService;
    }

    @Override
    public GetAllService<DiseaseHistory, DiseaseHistoryId> getServiceForGetAll() {
        return diseaseHistoryService;
    }

    @Override
    public GetService<GetDiseaseHistoryRequest, DiseaseHistory, DiseaseHistoryId> getServiceForGet() {
        return diseaseHistoryService;
    }

    @Override
    public List<DiseaseHistoryInfo> getForClient(BigInteger clientId) {
        return clientService.getOne(clientId)
                .map(diseaseHistoryService::getForClient)
                .map(histories -> histories.stream()
                        .map(history -> getConversionService().convert(history, DiseaseHistoryInfo.class))
                        .toList())
                .orElse(Collections.emptyList());
    }

}
