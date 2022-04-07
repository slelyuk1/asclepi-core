package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CrudController;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.response.disease.DiagnosisInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface  DiagnosisController extends CrudController<GetDiagnosisRequest, CreateDiagnosisRequest, EditDiagnosisRequest, DiagnosisInfo> {

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
