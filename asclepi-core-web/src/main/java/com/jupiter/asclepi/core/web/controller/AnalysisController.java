package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.request.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.request.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.AnalysisInfo;
import com.jupiter.asclepi.core.repository.entity.analysis.Analysis;
import com.jupiter.asclepi.core.repository.entity.analysis.AnalysisId;
import com.jupiter.asclepi.core.web.helper.api.CrudController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AnalysisController extends CrudController<Analysis, AnalysisId, GetAnalysisRequest, CreateAnalysisRequest, EditAnalysisRequest, AnalysisInfo> {

    @Override
    default Class<AnalysisInfo> getResponseClass() {
        return AnalysisInfo.class;
    }

    @Override
    default AnalysisInfo create(@RequestBody CreateAnalysisRequest createRequest) {
        return CrudController.super.create(createRequest);
    }

    @Override
    @DeleteMapping
    default void delete(@RequestBody GetAnalysisRequest deleteRequest) {
        CrudController.super.delete(deleteRequest);
    }

    @Override
    default List<AnalysisInfo> getAll() {
        return CrudController.super.getAll();
    }

    @Override
    @GetMapping
    default AnalysisInfo getOne(@RequestBody GetAnalysisRequest getRequest) {
        return CrudController.super.getOne(getRequest);
    }

    @Override
    default AnalysisInfo edit(@RequestBody EditAnalysisRequest editRequest) {
        return CrudController.super.edit(editRequest);
    }

    @GetMapping("/getForVisit")
    List<AnalysisInfo> getAnalysisForVisit(@RequestBody GetVisitRequest visitGetter);

    @GetMapping("/getForDiseaseHistory")
    List<AnalysisInfo> getAnalysisForDiseaseHistory(@RequestBody GetDiseaseHistoryRequest diseaseHistoryGetter);
}
