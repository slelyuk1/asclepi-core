package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CrudController;
import com.jupiter.asclepi.core.model.request.disease.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.request.disease.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.disease.AnalysisInfo;

import java.math.BigInteger;
import java.util.List;

public interface AnalysisController extends CrudController<BigInteger, CreateAnalysisRequest, EditAnalysisRequest, AnalysisInfo> {

    List<AnalysisInfo> getAnalysisForVisit(GetVisitRequest visitGetter);

    List<AnalysisInfo> getAnalysisForDiseaseHistory(GetDiseaseHistoryRequest diseaseHistoryGetter);
}
