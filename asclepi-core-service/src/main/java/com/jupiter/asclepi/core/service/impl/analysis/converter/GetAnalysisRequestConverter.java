package com.jupiter.asclepi.core.service.impl.analysis.converter;

import com.jupiter.asclepi.core.model.model.entity.analysis.AnalysisId;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.VisitId;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

@RequiredArgsConstructor
public class GetAnalysisRequestConverter implements Converter<GetAnalysisRequest, AnalysisId> {

    private final Converter<GetDiseaseHistoryRequest, DiseaseHistoryId> converter;
    private final Converter<GetVisitRequest, VisitId> converter2;

    @Override
    public AnalysisId convert(GetAnalysisRequest source) {
        GetDiseaseHistoryRequest historyGetter = source.getVisit().getDiseaseHistory();
        GetVisitRequest visitGetter = source.getVisit();
        VisitId visitId = Objects.requireNonNull(converter2.convert(visitGetter));

        DiseaseHistoryId historyId = Objects.requireNonNull(converter.convert(historyGetter));

        return new AnalysisId(historyId, visitId, source.getNumber());
    }
}
