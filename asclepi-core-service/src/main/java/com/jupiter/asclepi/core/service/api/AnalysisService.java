package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.model.request.disease.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.request.disease.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.request.disease.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.repository.entity.Analysis;
import com.jupiter.asclepi.core.repository.entity.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.Visit;
import com.jupiter.asclepi.core.service.helper.api.CrudService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AnalysisService extends CrudService<GetAnalysisRequest, CreateAnalysisRequest, EditAnalysisRequest, Analysis, Boolean> {
    List<Analysis> getForVisit(@NotNull Visit visit);

    List<Analysis> getForDiseaseHistory(@NotNull DiseaseHistory history);
}
