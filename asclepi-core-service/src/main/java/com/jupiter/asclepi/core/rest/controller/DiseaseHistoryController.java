package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CreateController;
import com.jupiter.asclepi.core.helper.api.business.controller.EditController;
import com.jupiter.asclepi.core.helper.api.business.controller.GetAllController;
import com.jupiter.asclepi.core.model.model.request.disease.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.response.disease.DiseaseHistoryInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

public interface DiseaseHistoryController extends
        GetAllController<DiseaseHistoryInfo>,
        CreateController<CreateDiseaseHistoryRequest>,
        EditController<EditDiseaseHistoryRequest> {

    @PostMapping("/create")
    @Override
    ResponseEntity<?> create(@NotNull @RequestBody CreateDiseaseHistoryRequest createRequest);

    @PostMapping("/edit")
    @Override
    ResponseEntity<?> edit(@NotNull @RequestBody EditDiseaseHistoryRequest editRequest);

    @GetMapping("/all")
    @Override
    List<DiseaseHistoryInfo> getAll();

    @GetMapping("/{clientId}/{historyNumber}")
    ResponseEntity<DiseaseHistoryInfo> getOne(@NotNull @PathVariable("clientId") BigInteger clientId,
                                              @NotNull @PathVariable("historyNumber") Integer historyNumber);

    @GetMapping("/{clientId}")
    List<DiseaseHistoryInfo> getForClient(@PathVariable("clientId") BigInteger clientId);

    // todo implement
    // ResponseEntity<?> abort(GetDiseaseHistoryRequest request);
    // ResponseEntity<?> close(GetDiseaseHistoryRequest request);
}
