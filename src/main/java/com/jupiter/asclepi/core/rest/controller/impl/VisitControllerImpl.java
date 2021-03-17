package com.jupiter.asclepi.core.rest.controller.impl;

import com.jupiter.asclepi.core.model.request.disease.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.response.disease.VisitInfo;
import com.jupiter.asclepi.core.rest.controller.VisitController;
import com.jupiter.asclepi.core.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/visit")
public class VisitControllerImpl implements VisitController {

    private final VisitService visitService;

    @Override
    public ResponseEntity<?> create(@NotNull CreateVisitRequest createRequest) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<?> edit(@NotNull EditVisitRequest editRequest) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<VisitInfo> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<VisitInfo> getOne(@NotNull BigInteger clientId, @NotNull Integer diseaseHistoryNumber, @NotNull Integer number) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<VisitInfo> getByDiseaseHistory(@NotNull BigInteger diseaseHistoryId, @NotNull Integer diseaseHistoryNumber) {
        throw new UnsupportedOperationException();
    }
}
