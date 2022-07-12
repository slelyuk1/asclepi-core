package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.AnamnesisInfo;
import com.jupiter.asclepi.core.repository.entity.Anamnesis;
import com.jupiter.asclepi.core.service.api.AnamnesisService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.helper.api.CreateService;
import com.jupiter.asclepi.core.service.helper.api.DeleteService;
import com.jupiter.asclepi.core.service.helper.api.GetService;
import com.jupiter.asclepi.core.web.controller.AnamnesisController;
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
@RequestMapping("/api/v1/anamnesis")
public class AnamnesisControllerImpl extends AbstractController<AnamnesisInfo> implements AnamnesisController {

    private final AnamnesisService anamnesisService;
    private final DiseaseHistoryService diseaseHistoryService;

    public AnamnesisControllerImpl(ConversionService conversionService, AnamnesisService anamnesisService, DiseaseHistoryService diseaseHistoryService) {
        super(conversionService);
        this.anamnesisService = anamnesisService;
        this.diseaseHistoryService = diseaseHistoryService;
    }

    @Override
    public CreateService<CreateAnamnesisRequest, Anamnesis, BigInteger> getServiceForCreation() {
        return anamnesisService;
    }

    @Override
    public DeleteService<BigInteger, Anamnesis, BigInteger> getServiceForDelete() {
        return anamnesisService;
    }

    @Override
    public GetService<BigInteger, Anamnesis, BigInteger> getServiceForGet() {
        return anamnesisService;
    }

    @Override
    public List<AnamnesisInfo> getForDiseaseHistory(@NotNull GetDiseaseHistoryRequest request) {
        return diseaseHistoryService.getOne(request)
                .map(anamnesisService::getForDiseaseHistory)
                .map(anamnesisList -> anamnesisList.stream()
                        .map(anamnesis -> getConversionService().convert(anamnesis, AnamnesisInfo.class))
                        .toList())
                .orElse(Collections.emptyList());
    }
}
