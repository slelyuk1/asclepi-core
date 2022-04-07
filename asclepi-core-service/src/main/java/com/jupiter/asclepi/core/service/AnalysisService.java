package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CrudService;
import com.jupiter.asclepi.core.model.model.entity.analysis.Analysis;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.GetAnalysisRequest;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AnalysisService extends CrudService<GetAnalysisRequest, CreateAnalysisRequest, EditAnalysisRequest, Analysis, Boolean> {
    List<Analysis> getForVisit(@NotNull Visit visit);

    List<Analysis> getForDiseaseHistory(@NotNull DiseaseHistory history);
}
