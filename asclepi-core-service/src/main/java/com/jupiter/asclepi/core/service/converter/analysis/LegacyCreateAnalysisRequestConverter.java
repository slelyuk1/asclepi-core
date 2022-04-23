package com.jupiter.asclepi.core.service.converter.analysis;

import com.jupiter.asclepi.core.model.model.entity.analysis.Analysis;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.VisitId;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

@RequiredArgsConstructor
public class LegacyCreateAnalysisRequestConverter implements Converter<CreateAnalysisRequest, Analysis>  {

    private final Converter<GetDiseaseHistoryRequest, DiseaseHistoryId> converter;
    private final Converter<GetVisitRequest, VisitId> converter2;
    @Override
    public Analysis convert(CreateAnalysisRequest source) {


        DiseaseHistoryId diseaseHistoryId = Objects.requireNonNull(converter.convert(source.getVisit().getDiseaseHistory()));
        VisitId visitId = Objects.requireNonNull(converter2.convert(source.getVisit()));

        Visit visit = new Visit(visitId);
        visit.setDiseaseHistory(new DiseaseHistory(diseaseHistoryId));

        Analysis analysis = new Analysis();
        analysis.setVisit(visit);


        analysis.setTitle(source.getTitle());
        analysis.setSummary(source.getSummary());
        return analysis;
    }
}
