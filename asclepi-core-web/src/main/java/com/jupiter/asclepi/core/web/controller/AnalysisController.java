package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.request.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.request.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.AnalysisInfo;
import com.jupiter.asclepi.core.web.helper.api.crud.CrudUsingRequestBodyController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AnalysisController extends CrudUsingRequestBodyController<GetAnalysisRequest, CreateAnalysisRequest, EditAnalysisRequest, AnalysisInfo> {

    @GetMapping("/getForVisit")
    List<AnalysisInfo> getAnalysisForVisit(@NotNull @RequestBody GetVisitRequest visitGetter);

    @GetMapping("/getForDiseaseHistory")
    List<AnalysisInfo> getAnalysisForDiseaseHistory(@NotNull @RequestBody GetDiseaseHistoryRequest diseaseHistoryGetter);
}
