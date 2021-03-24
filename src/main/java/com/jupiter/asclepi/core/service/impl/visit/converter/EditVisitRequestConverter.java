package com.jupiter.asclepi.core.service.impl.visit.converter;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import org.springframework.core.convert.converter.Converter;

public class EditVisitRequestConverter implements Converter<EditVisitRequest, Visit> {
    @Override
    public Visit convert(EditVisitRequest source) {
        GetVisitRequest visitGetter = source.getVisit();
        GetDiseaseHistoryRequest historyGetter = visitGetter.getDiseaseHistory();

        DiseaseHistory history = new DiseaseHistory(new DiseaseHistoryId(historyGetter.getClientId(), historyGetter.getNumber()));
        Visit visit = new Visit();
        visit.setDiseaseHistory(history);
        visit.setNumber(visitGetter.getNumber());
        visit.setWhen(source.getWhen());
        return visit;
    }
}
