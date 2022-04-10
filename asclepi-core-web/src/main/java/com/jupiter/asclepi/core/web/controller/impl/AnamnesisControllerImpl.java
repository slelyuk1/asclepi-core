package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.model.request.disease.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.response.disease.AnamnesisInfo;
import com.jupiter.asclepi.core.service.api.AnamnesisService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.exception.AsclepiRuntimeException;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.web.controller.AnamnesisController;
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
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/anamnesis")
@RequiredArgsConstructor
public class AnamnesisControllerImpl implements AnamnesisController {

    private final AnamnesisService anamnesisService;
    private final DiseaseHistoryService diseaseHistoryService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<?> create(@NotNull CreateAnamnesisRequest createRequest) {
        Try<ResponseEntity<?>> creationTry = anamnesisService.create(createRequest).map(anamnesis -> {
            AnamnesisInfo anamnesisInfo = conversionService.convert(anamnesis, AnamnesisInfo.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(anamnesisInfo);
        });
        return creationTry
                .recover(NonExistentIdException.class, e -> ControllerUtils.notFoundResponse())
                .onFailure(e -> log.error("An error occurred during anamnesis creation: ", e))
                .getOrElseThrow(AsclepiRuntimeException::new);
    }

    @Override
    public ResponseEntity<?> delete(@NotNull BigInteger anamnesisId) {
        boolean result = anamnesisService.delete(anamnesisId);
        return result ? ResponseEntity.ok().build() : ControllerUtils.notFoundResponse();
    }

    @Override
    public ResponseEntity<AnamnesisInfo> getOne(@NotNull BigInteger anamnesisId) {
        return anamnesisService.getOne(anamnesisId)
                .map(anamnesis -> conversionService.convert(anamnesis, AnamnesisInfo.class))
                .map(ResponseEntity::ok)
                .orElse(ControllerUtils.notFoundResponse());
    }

    @Override
    public List<AnamnesisInfo> getForDiseaseHistory(@NotNull GetDiseaseHistoryRequest request) {
        return diseaseHistoryService.getOne(request)
                .map(anamnesisService::getForDiseaseHistory)
                .map(anamnesisList -> anamnesisList.stream()
                        .map(anamnesis -> conversionService.convert(anamnesis, AnamnesisInfo.class))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
