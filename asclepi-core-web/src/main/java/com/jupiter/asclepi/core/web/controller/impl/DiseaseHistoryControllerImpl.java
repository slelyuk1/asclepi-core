package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.DiseaseHistoryInfo;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.service.api.ClientService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.web.controller.DiseaseHistoryController;
import com.jupiter.asclepi.core.web.util.ControllerUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/diseaseHistory")
public class DiseaseHistoryControllerImpl implements DiseaseHistoryController {

    private final ClientService clientService;
    private final DiseaseHistoryService diseaseHistoryService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<DiseaseHistoryInfo> create(@NotNull CreateDiseaseHistoryRequest createRequest) {
        DiseaseHistory diseaseHistory = diseaseHistoryService.create(createRequest);
        DiseaseHistoryInfo historyInfo = conversionService.convert(diseaseHistory, DiseaseHistoryInfo.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(historyInfo);
    }

    @Override
    public ResponseEntity<DiseaseHistoryInfo> edit(@NotNull EditDiseaseHistoryRequest editRequest) {
        DiseaseHistory edited = diseaseHistoryService.edit(editRequest);
        DiseaseHistoryInfo historyInfo = conversionService.convert(edited, DiseaseHistoryInfo.class);
        return ResponseEntity.ok().body(historyInfo);
    }

    @Override
    public List<DiseaseHistoryInfo> getAll() {
        return diseaseHistoryService.getAll().stream()
                .map(history -> conversionService.convert(history, DiseaseHistoryInfo.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<DiseaseHistoryInfo> getOne(@NotNull GetDiseaseHistoryRequest request) {
        return diseaseHistoryService.getOne(request)
                .map(history -> conversionService.convert(history, DiseaseHistoryInfo.class))
                .map(ResponseEntity::ok)
                .orElse(ControllerUtils.notFoundResponse());
    }

    @Override
    public List<DiseaseHistoryInfo> getForClient(BigInteger clientId) {
        return clientService.getOne(clientId)
                .map(diseaseHistoryService::getForClient)
                .map(histories -> histories.stream()
                        .map(history -> conversionService.convert(history, DiseaseHistoryInfo.class))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

}
