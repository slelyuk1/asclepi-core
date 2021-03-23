package com.jupiter.asclepi.core.service.impl.visit.converter;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.entity.disease.visit.VisitId;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@RequiredArgsConstructor
public class GetVisitRequestConverter implements Converter<GetVisitRequest, VisitId> {

    private final Converter<GetDiseaseHistoryRequest, DiseaseHistoryId> historyIdConverter;

    @Override
    public VisitId convert(GetVisitRequest source) {
        GetDiseaseHistoryRequest historyGetter = source.getDiseaseHistory();
        DiseaseHistory history = new DiseaseHistory(new Client(historyGetter.getClientId()), historyGetter.getNumber());
        return new VisitId(historyIdConverter.convert(source.getDiseaseHistory()), source.getNumber());
    }
}
