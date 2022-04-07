package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CreateController;
import com.jupiter.asclepi.core.helper.api.business.controller.DeleteController;
import com.jupiter.asclepi.core.helper.api.business.controller.GetController;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.disease.AnamnesisInfo;
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
