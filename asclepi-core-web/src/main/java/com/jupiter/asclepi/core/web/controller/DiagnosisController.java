package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.DiagnosisInfo;
import com.jupiter.asclepi.core.repository.entity.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.repository.entity.diagnosis.DiagnosisId;
import com.jupiter.asclepi.core.web.helper.api.CrudController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface DiagnosisController extends CrudController<Diagnosis, DiagnosisId, GetDiagnosisRequest, CreateDiagnosisRequest, EditDiagnosisRequest, DiagnosisInfo> {

    @Override
    default Class<DiagnosisInfo> getResponseClass() {
        return DiagnosisInfo.class;
    }

    @Override
    default DiagnosisInfo create(@RequestBody CreateDiagnosisRequest createRequest) {
        return CrudController.super.create(createRequest);
    }

    @Override
    @DeleteMapping
    default void delete(@RequestBody GetDiagnosisRequest deleteRequest) {
        CrudController.super.delete(deleteRequest);
    }

    @Override
    default List<DiagnosisInfo> getAll() {
        return CrudController.super.getAll();
    }

    @Override
    @GetMapping
    default DiagnosisInfo getOne(@RequestBody GetDiagnosisRequest getRequest) {
        return CrudController.super.getOne(getRequest);
    }

    @Override
    default DiagnosisInfo edit(@RequestBody EditDiagnosisRequest editRequest) {
        return CrudController.super.edit(editRequest);
    }

    @GetMapping("/getForDiseaseHistory")
    List<DiagnosisInfo> getForDiseaseHistory(@NotNull @RequestBody GetDiseaseHistoryRequest request);
}
