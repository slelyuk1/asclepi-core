package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.DiseaseHistoryInfo;
import com.jupiter.asclepi.core.web.helper.api.CreateController;
import com.jupiter.asclepi.core.web.helper.api.EditController;
import com.jupiter.asclepi.core.web.helper.api.GetAllController;
import com.jupiter.asclepi.core.web.helper.api.get.GetUsingRequestBodyController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

public interface DiseaseHistoryController extends
        GetUsingRequestBodyController<GetDiseaseHistoryRequest, DiseaseHistoryInfo>,
        GetAllController<DiseaseHistoryInfo>,
        CreateController<CreateDiseaseHistoryRequest, DiseaseHistoryInfo>,
        EditController<EditDiseaseHistoryRequest> {

    @PostMapping("/edit")
    @Override
    ResponseEntity<?> edit(@NotNull @RequestBody EditDiseaseHistoryRequest editRequest);

    @GetMapping("/all")
    @Override
    List<DiseaseHistoryInfo> getAll();

    @GetMapping("/{clientId}")
    List<DiseaseHistoryInfo> getForClient(@PathVariable("clientId") BigInteger clientId);

    // todo implement
    // ResponseEntity<?> abort(GetDiseaseHistoryRequest request);
    // ResponseEntity<?> close(GetDiseaseHistoryRequest request);
}
