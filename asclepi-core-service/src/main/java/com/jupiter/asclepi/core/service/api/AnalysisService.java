package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.model.request.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.request.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.request.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.repository.entity.analysis.Analysis;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.service.helper.api.CrudService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AnalysisService extends CrudService<GetAnalysisRequest, CreateAnalysisRequest, EditAnalysisRequest, Analysis, Boolean> {
    List<Analysis> getForVisit(@NotNull Visit visit);

    List<Analysis> getForDiseaseHistory(@NotNull DiseaseHistory history);
}
