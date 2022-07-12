package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.VisitInfo;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.repository.entity.visit.VisitId;
import com.jupiter.asclepi.core.web.helper.api.CreateController;
import com.jupiter.asclepi.core.web.helper.api.GetAllController;
import com.jupiter.asclepi.core.web.helper.api.GetController;
import com.jupiter.asclepi.core.web.helper.api.PatchController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

public interface VisitController extends
        GetController<GetVisitRequest, Visit, VisitId, VisitInfo>,
        GetAllController<VisitInfo, Visit, VisitId>,
        CreateController<CreateVisitRequest, Visit, VisitId, VisitInfo>,
        PatchController<EditVisitRequest, Visit, VisitId, VisitInfo> {

    @Override
    default Class<VisitInfo> getResponseClass() {
        return VisitInfo.class;
    }

    @Override
    default VisitInfo create(@RequestBody CreateVisitRequest createRequest) {
        return CreateController.super.create(createRequest);
    }

    @Override
    default List<VisitInfo> getAll() {
        return GetAllController.super.getAll();
    }

    @Override
    @GetMapping
    default VisitInfo getOne(@RequestBody GetVisitRequest getRequest) {
        return GetController.super.getOne(getRequest);
    }

    @Override
    default VisitInfo edit(@RequestBody EditVisitRequest editRequest) {
        return PatchController.super.edit(editRequest);
    }

    @GetMapping("/{clientId}/{diseaseHistoryNumber}")
    List<VisitInfo> getForDiseaseHistory(@NotNull @PathVariable("clientId") BigInteger clientId,
                                         @NotNull @PathVariable("diseaseHistoryNumber") Integer diseaseHistoryNumber);

    // todo implement
    // ResponseEntity<?> finish(BigInteger diseaseHistoryId);
}
