package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.DiseaseHistoryInfo;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistoryId;
import com.jupiter.asclepi.core.web.helper.api.CreateController;
import com.jupiter.asclepi.core.web.helper.api.GetAllController;
import com.jupiter.asclepi.core.web.helper.api.GetController;
import com.jupiter.asclepi.core.web.helper.api.PatchController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.List;

public interface DiseaseHistoryController extends
        GetController<GetDiseaseHistoryRequest, DiseaseHistory, DiseaseHistoryId, DiseaseHistoryInfo>,
        GetAllController<DiseaseHistoryInfo, DiseaseHistory, DiseaseHistoryId>,
        CreateController<CreateDiseaseHistoryRequest, DiseaseHistory, DiseaseHistoryId, DiseaseHistoryInfo>,
        PatchController<EditDiseaseHistoryRequest, DiseaseHistory, DiseaseHistoryId, DiseaseHistoryInfo> {

    @Override
    default Class<DiseaseHistoryInfo> getResponseClass() {
        return DiseaseHistoryInfo.class;
    }

    @Override
    default DiseaseHistoryInfo create(@RequestBody CreateDiseaseHistoryRequest createRequest) {
        return CreateController.super.create(createRequest);
    }

    @Override
    default List<DiseaseHistoryInfo> getAll() {
        return GetAllController.super.getAll();
    }

    @Override
    @GetMapping
    default DiseaseHistoryInfo getOne(@RequestBody GetDiseaseHistoryRequest getRequest) {
        return GetController.super.getOne(getRequest);
    }

    @Override
    default DiseaseHistoryInfo edit(@RequestBody EditDiseaseHistoryRequest editRequest) {
        return PatchController.super.edit(editRequest);
    }

    @GetMapping("/{clientId}")
    List<DiseaseHistoryInfo> getForClient(@PathVariable("clientId") BigInteger clientId);

    // todo implement
    // ResponseEntity<?> abort(GetDiseaseHistoryRequest request);
    // ResponseEntity<?> close(GetDiseaseHistoryRequest request);
}
