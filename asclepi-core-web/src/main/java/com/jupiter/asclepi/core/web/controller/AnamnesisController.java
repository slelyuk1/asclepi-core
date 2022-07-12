package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.AnamnesisInfo;
import com.jupiter.asclepi.core.repository.entity.Anamnesis;
import com.jupiter.asclepi.core.web.helper.api.CreateController;
import com.jupiter.asclepi.core.web.helper.api.DeleteController;
import com.jupiter.asclepi.core.web.helper.api.GetController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

public interface AnamnesisController extends
        GetController<BigInteger, Anamnesis, BigInteger, AnamnesisInfo>,
        CreateController<CreateAnamnesisRequest, Anamnesis, BigInteger, AnamnesisInfo>,
        DeleteController<BigInteger, Anamnesis, BigInteger> {

    @Override
    default Class<AnamnesisInfo> getResponseClass() {
        return AnamnesisInfo.class;
    }

    @Override
    default AnamnesisInfo create(@RequestBody CreateAnamnesisRequest createRequest) {
        return CreateController.super.create(createRequest);
    }

    @Override
    @DeleteMapping("/{id}")
    default void delete(@PathVariable("id") BigInteger deleteRequest) {
        DeleteController.super.delete(deleteRequest);
    }

    @Override
    @GetMapping("/{id}")
    default AnamnesisInfo getOne(@PathVariable("id") BigInteger getRequest) {
        return GetController.super.getOne(getRequest);
    }

    @GetMapping("/getForDiseaseHistory")
    List<AnamnesisInfo> getForDiseaseHistory(@NotNull @RequestBody GetDiseaseHistoryRequest request);
}
