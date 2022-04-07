package com.jupiter.asclepi.core.service.impl.analysis.converter;

import com.jupiter.asclepi.core.model.model.entity.analysis.Analysis;
import com.jupiter.asclepi.core.model.model.entity.analysis.AnalysisId;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.VisitId;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

@RequiredArgsConstructor
public class EditAnalysisRequestConverter implements Converter<EditAnalysisRequest, Analysis> {

    private final Converter<GetDiseaseHistoryRequest, DiseaseHistoryId> converter;
    private final Converter<GetVisitRequest, VisitId> converter2;

    @Override
    public Analysis convert(EditAnalysisRequest source) {

        GetAnalysisRequest analysisGetter = source.getAnalysis();
        GetDiseaseHistoryRequest historyGetter = analysisGetter.getVisit().getDiseaseHistory();
        GetVisitRequest visitGetter = analysisGetter.getVisit();

        DiseaseHistoryId diseaseHistoryId = Objects.requireNonNull(converter.convert(historyGetter));
        VisitId visitId = Objects.requireNonNull(converter2.convert(visitGetter));

        AnalysisId analysisId = new AnalysisId(diseaseHistoryId, visitId, analysisGetter.getNumber());

        Analysis analysis = new Analysis(analysisId);

        analysis.setTitle(source.getTitle());
        analysis.setSummary(source.getSummary());

        return analysis;
    }
}
