package com.jupiter.asclepi.core.service.impl.analysis.converter;

import com.jupiter.asclepi.core.model.model.entity.analysis.Analysis;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.model.response.disease.AnalysisInfo;
import org.springframework.core.convert.converter.Converter;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import lombok.RequiredArgsConstructor;

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
