package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.DiseaseHistoryInfo;
import com.jupiter.asclepi.core.web.helper.api.CreateController;
import com.jupiter.asclepi.core.web.helper.api.GetAllController;
import com.jupiter.asclepi.core.web.helper.api.PatchController;
import com.jupiter.asclepi.core.web.helper.api.get.GetUsingRequestBodyController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;
import java.util.List;

public interface DiseaseHistoryController extends
        GetUsingRequestBodyController<GetDiseaseHistoryRequest, DiseaseHistoryInfo>,
        GetAllController<DiseaseHistoryInfo>,
        CreateController<CreateDiseaseHistoryRequest, DiseaseHistoryInfo>,
        PatchController<EditDiseaseHistoryRequest, DiseaseHistoryInfo> {

    @GetMapping("/{clientId}")
    List<DiseaseHistoryInfo> getForClient(@PathVariable("clientId") BigInteger clientId);

    // todo implement
    // ResponseEntity<?> abort(GetDiseaseHistoryRequest request);
    // ResponseEntity<?> close(GetDiseaseHistoryRequest request);
}
