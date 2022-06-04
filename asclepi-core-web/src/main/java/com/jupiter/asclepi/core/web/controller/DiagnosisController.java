package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.DiagnosisInfo;
import com.jupiter.asclepi.core.web.helper.api.CrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface DiagnosisController extends CrudController<GetDiagnosisRequest, CreateDiagnosisRequest, EditDiagnosisRequest, DiagnosisInfo> {

    @Override
    @PostMapping("/create")
    ResponseEntity<?> create(@NotNull @RequestBody CreateDiagnosisRequest createRequest);

    @DeleteMapping("/delete")
    @Override
    ResponseEntity<?> delete(@NotNull @RequestBody GetDiagnosisRequest deleteRequest);

    @PostMapping("/edit")
    @Override
    ResponseEntity<?> edit(@NotNull @RequestBody EditDiagnosisRequest editRequest);

    @GetMapping("/all")
    @Override
    List<DiagnosisInfo> getAll();

    @GetMapping("/get")
    @Override
    ResponseEntity<DiagnosisInfo> getOne(@NotNull @RequestBody GetDiagnosisRequest getRequest);

    @GetMapping("/getForDiseaseHistory")
    List<DiagnosisInfo> getForDiseaseHistory(@NotNull @RequestBody GetDiseaseHistoryRequest request);
}
