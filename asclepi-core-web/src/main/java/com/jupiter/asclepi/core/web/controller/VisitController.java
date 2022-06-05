package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.VisitInfo;
import com.jupiter.asclepi.core.web.helper.api.CreateController;
import com.jupiter.asclepi.core.web.helper.api.GetAllController;
import com.jupiter.asclepi.core.web.helper.api.PatchController;
import com.jupiter.asclepi.core.web.helper.api.get.GetUsingRequestBodyController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

public interface VisitController extends
        GetUsingRequestBodyController<GetVisitRequest, VisitInfo>,
        GetAllController<VisitInfo>,
        CreateController<CreateVisitRequest, VisitInfo>,
        PatchController<EditVisitRequest, VisitInfo> {

    @GetMapping("/{clientId}/{diseaseHistoryNumber}")
    List<VisitInfo> getForDiseaseHistory(@NotNull @PathVariable("clientId") BigInteger clientId,
                                         @NotNull @PathVariable("diseaseHistoryNumber") Integer diseaseHistoryNumber);

    // todo implement
    // ResponseEntity<?> finish(BigInteger diseaseHistoryId);
}
