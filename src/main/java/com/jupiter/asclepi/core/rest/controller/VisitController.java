package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CreateController;
import com.jupiter.asclepi.core.helper.api.business.controller.EditController;
import com.jupiter.asclepi.core.helper.api.business.controller.GetAllController;
import com.jupiter.asclepi.core.model.request.disease.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.response.disease.VisitInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

public interface VisitController extends
        GetAllController<VisitInfo>,
        CreateController<CreateVisitRequest>,
        EditController<EditVisitRequest> {

    @PostMapping("/create")
    @Override
    ResponseEntity<?> create(@NotNull @RequestBody CreateVisitRequest createRequest);

    @PostMapping("/edit")
    @Override
    ResponseEntity<?> edit(@NotNull @RequestBody EditVisitRequest editRequest);

    @GetMapping("/all")
    @Override
    List<VisitInfo> getAll();

    @GetMapping("/{clientId}/{diseaseHistoryNumber}/{number}")
    ResponseEntity<VisitInfo> getOne(@NotNull @PathVariable("clientId") BigInteger clientId,
                                     @NotNull @PathVariable("diseaseHistoryNumber") Integer diseaseHistoryNumber,
                                     @NotNull @PathVariable("number") Integer number);

    @GetMapping("/{clientId}/{diseaseHistoryNumber}")
    List<VisitInfo> getForDiseaseHistory(@NotNull @PathVariable("clientId") BigInteger clientId,
                                         @NotNull @PathVariable("diseaseHistoryNumber") Integer diseaseHistoryNumber);

    // todo implement
    // ResponseEntity<?> finish(BigInteger diseaseHistoryId);
}
