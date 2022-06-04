package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.AnamnesisInfo;
import com.jupiter.asclepi.core.web.helper.api.CreateController;
import com.jupiter.asclepi.core.web.helper.api.DeleteController;
import com.jupiter.asclepi.core.web.helper.api.GetController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

public interface AnamnesisController extends
        GetController<BigInteger, AnamnesisInfo>,
        CreateController<CreateAnamnesisRequest>,
        DeleteController<BigInteger> {

    @Override
    @PostMapping("/create")
    ResponseEntity<?> create(@NotNull @RequestBody CreateAnamnesisRequest createRequest);

    @Override
    @DeleteMapping("/{anamnesisId}/delete")
    ResponseEntity<?> delete(@NotNull @PathVariable("anamnesisId") BigInteger anamnesisId);

    @Override
    @GetMapping("/{anamnesisId}")
    ResponseEntity<AnamnesisInfo> getOne(@NotNull @PathVariable("anamnesisId") BigInteger anamnesisId);

    @GetMapping("/getForDiseaseHistory")
    List<AnamnesisInfo> getForDiseaseHistory(@NotNull @RequestBody GetDiseaseHistoryRequest request);
}
