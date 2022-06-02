package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.disease.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.request.disease.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.request.disease.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.disease.AnalysisInfo;
import com.jupiter.asclepi.core.web.helper.api.CrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AnalysisController extends CrudController<GetAnalysisRequest, CreateAnalysisRequest, EditAnalysisRequest, AnalysisInfo> {

    @PostMapping("/create")
    @Override
    ResponseEntity<?> create(@NotNull @RequestBody CreateAnalysisRequest createRequest);

    @DeleteMapping("/delete")
    @Override
    ResponseEntity<?> delete(@NotNull @RequestBody GetAnalysisRequest deleteRequest);

    @PostMapping("/edit")
    @Override
    ResponseEntity<?> edit(@NotNull @RequestBody EditAnalysisRequest editRequest);

    @GetMapping("/all")
    @Override
    List<AnalysisInfo> getAll();

    @GetMapping("/get")
    @Override
    ResponseEntity<AnalysisInfo> getOne(@NotNull @RequestBody GetAnalysisRequest getRequest);

    @GetMapping("/getForVisit")
    List<AnalysisInfo> getAnalysisForVisit(@NotNull @RequestBody GetVisitRequest visitGetter);

    @GetMapping("/getForDiseaseHistory")
    List<AnalysisInfo> getAnalysisForDiseaseHistory(@NotNull @RequestBody GetDiseaseHistoryRequest diseaseHistoryGetter);
}
