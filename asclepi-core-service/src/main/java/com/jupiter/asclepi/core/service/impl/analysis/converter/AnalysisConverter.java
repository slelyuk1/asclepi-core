package com.jupiter.asclepi.core.service.impl.analysis.converter;

import com.jupiter.asclepi.core.model.entity.analysis.Analysis;
import com.jupiter.asclepi.core.model.entity.analysis.AnalysisId;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.request.disease.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.request.disease.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.disease.AnalysisInfo;
import org.springframework.core.convert.converter.Converter;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class AnalysisConverter implements Converter<Analysis, AnalysisInfo> {

    @Override
    public AnalysisInfo convert(Analysis source) {
        DiseaseHistory history = source.getVisit().getDiseaseHistory();
        GetAnalysisRequest analysisGetter = new GetAnalysisRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getNumber()),
                        source.getVisit().getNumber()
                ),
                source.getNumber()
        );

        return new AnalysisInfo(analysisGetter, source.getTitle(), source.getSummary());
    }
}
