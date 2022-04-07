package com.jupiter.asclepi.core.rest.controller.impl;

import com.jupiter.asclepi.core.exception.AsclepiRuntimeException;
import com.jupiter.asclepi.core.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.model.response.disease.VisitInfo;
import com.jupiter.asclepi.core.rest.controller.VisitController;
import com.jupiter.asclepi.core.util.ControllerUtils;
import com.jupiter.asclepi.core.service.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.VisitService;
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
@RequestMapping("/api/v1/visit")
public class VisitControllerImpl implements VisitController {

    private final DiseaseHistoryService diseaseHistoryService;
    private final VisitService visitService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<?> create(@NotNull CreateVisitRequest createRequest) {
        Try<ResponseEntity<?>> creationTry = visitService.create(createRequest).map(visit -> {
            VisitInfo visitInfo = conversionService.convert(visit, VisitInfo.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(visitInfo);
        });
        return creationTry
                .recover(NonExistentIdException.class, e -> ControllerUtils.notFoundResponse())
                .onFailure(e -> log.error("An error occurred during disease history creation: ", e))
                .getOrElseThrow(AsclepiRuntimeException::new);
    }

    @Override
    public ResponseEntity<?> edit(@NotNull EditVisitRequest editRequest) {
        Try<ResponseEntity<?>> editionTry = visitService.edit(editRequest).map(edited -> {
            VisitInfo info = conversionService.convert(edited, VisitInfo.class);
            return ResponseEntity.ok().body(info);
        });
        return editionTry
                .recover(NonExistentIdException.class, e -> ControllerUtils.notFoundResponse())
                .onFailure(e -> log.error("An error occurred during disease history creation: ", e))
                .getOrElseThrow(AsclepiRuntimeException::new);
    }

    @Override
    public List<VisitInfo> getAll() {
        return visitService.getAll().stream()
                .map(visit -> conversionService.convert(visit, VisitInfo.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<VisitInfo> getOne(@NotNull BigInteger clientId, @NotNull Integer diseaseHistoryNumber, @NotNull Integer number) {
        GetVisitRequest request = new GetVisitRequest(new GetDiseaseHistoryRequest(clientId, diseaseHistoryNumber), number);
        return visitService.getOne(request)
                .map(visit -> conversionService.convert(visit, VisitInfo.class))
                .map(ResponseEntity::ok)
                .orElse(ControllerUtils.notFoundResponse());
    }

    @Override
    public List<VisitInfo> getForDiseaseHistory(@NotNull BigInteger clientId, @NotNull Integer diseaseHistoryNumber) {
        GetDiseaseHistoryRequest historyGetter = new GetDiseaseHistoryRequest(clientId, diseaseHistoryNumber);
        return diseaseHistoryService.getOne(historyGetter)
                .map(visitService::getForDiseaseHistory)
                .map(visits -> visits.stream()
                        .map(visit -> conversionService.convert(visit, VisitInfo.class))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
