package com.jupiter.asclepi.core.rest.controller.impl;

import com.jupiter.asclepi.core.exception.AsclepiRuntimeException;
import com.jupiter.asclepi.core.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.model.model.request.disease.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.response.disease.DiseaseHistoryInfo;
import com.jupiter.asclepi.core.rest.controller.DiseaseHistoryController;
import com.jupiter.asclepi.core.util.ControllerUtils;
import com.jupiter.asclepi.core.service.ClientService;
import com.jupiter.asclepi.core.service.DiseaseHistoryService;
import io.vavr.control.Try;
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
    public ResponseEntity<?> create(@NotNull CreateDiseaseHistoryRequest createRequest) {
        Try<ResponseEntity<?>> creationTry = diseaseHistoryService.create(createRequest).map(diseaseHistory -> {
            DiseaseHistoryInfo historyInfo = conversionService.convert(diseaseHistory, DiseaseHistoryInfo.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(historyInfo);
        });
        return creationTry
                .recover(NonExistentIdException.class, e -> ControllerUtils.notFoundResponse())
                .onFailure(e -> log.error("An error occurred during disease history creation: ", e))
                .getOrElseThrow(AsclepiRuntimeException::new);
    }

    @Override
    public ResponseEntity<?> edit(@NotNull EditDiseaseHistoryRequest editRequest) {
        Try<ResponseEntity<?>> editionTry = diseaseHistoryService.edit(editRequest).map(edited -> {
            DiseaseHistoryInfo historyInfo = conversionService.convert(edited, DiseaseHistoryInfo.class);
            return ResponseEntity.ok().body(historyInfo);
        });
        return editionTry
                .recover(NonExistentIdException.class, e -> ControllerUtils.notFoundResponse())
                .onFailure(e -> log.error("An error occurred during disease history creation: ", e))
                .getOrElseThrow(AsclepiRuntimeException::new);
    }

    @Override
    public List<DiseaseHistoryInfo> getAll() {
        return diseaseHistoryService.getAll().stream()
                .map(history -> conversionService.convert(history, DiseaseHistoryInfo.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<DiseaseHistoryInfo> getOne(@NotNull BigInteger clientId, @NotNull Integer historyNumber) {
        GetDiseaseHistoryRequest request = new GetDiseaseHistoryRequest();
        request.setClientId(clientId);
        request.setNumber(historyNumber);
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
