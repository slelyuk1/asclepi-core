package com.jupiter.asclepi.core.service.converter.visit;

import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.model.response.disease.VisitInfo;
import org.springframework.core.convert.converter.Converter;

public class LegacyVisitConverter implements Converter<Visit, VisitInfo> {
    @Override
    public VisitInfo convert(Visit source) {
        GetVisitRequest visitGetter = new GetVisitRequest();
        visitGetter.setNumber(source.getNumber());
        GetDiseaseHistoryRequest historyGetter = new GetDiseaseHistoryRequest();
        historyGetter.setClientId(source.getDiseaseHistory().getClient().getId());
        historyGetter.setNumber(source.getDiseaseHistory().getNumber());
        visitGetter.setDiseaseHistory(historyGetter);
        return new VisitInfo(visitGetter, source.getWhen());
    }
}
