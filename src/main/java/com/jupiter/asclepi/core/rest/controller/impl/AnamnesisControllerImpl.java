package com.jupiter.asclepi.core.rest.controller.impl;

import com.jupiter.asclepi.core.model.request.disease.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.disease.AnamnesisInfo;
import com.jupiter.asclepi.core.rest.controller.AnamnesisController;
import com.jupiter.asclepi.core.service.AnamnesisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/anamnesis")
@RequiredArgsConstructor
public class AnamnesisControllerImpl implements AnamnesisController {

    private final AnamnesisService anamnesisService;

    @Override
    public ResponseEntity<?> create(@NotNull CreateAnamnesisRequest createRequest) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<?> delete(@NotNull BigInteger anamnesisId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<AnamnesisInfo> getOne(@NotNull BigInteger anamnesisId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<AnamnesisInfo> getForDiseaseHistory(@NotNull GetDiseaseHistoryRequest request) {
        throw new UnsupportedOperationException();
    }
}
