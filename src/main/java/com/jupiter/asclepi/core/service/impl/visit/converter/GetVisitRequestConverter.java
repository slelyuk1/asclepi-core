package com.jupiter.asclepi.core.service.impl.visit.converter;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import org.springframework.core.convert.converter.Converter;

public class GetVisitRequestConverter implements Converter<GetVisitRequest, Visit> {
    @Override
    public Visit convert(GetVisitRequest source) {
        GetDiseaseHistoryRequest historyGetter = source.getDiseaseHistory();
        DiseaseHistory history = new DiseaseHistory(new Client(historyGetter.getClientId()), historyGetter.getNumber());
        return new Visit(history, source.getNumber());
    }
}
