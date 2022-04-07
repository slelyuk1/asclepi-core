package com.jupiter.asclepi.core.service.impl.visit.converter;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.CreateVisitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

@RequiredArgsConstructor
public class CreateVisitRequestConverter implements Converter<CreateVisitRequest, Visit> {

    private final Converter<GetDiseaseHistoryRequest, DiseaseHistoryId> converter;

    @Override
    public Visit convert(CreateVisitRequest source) {
        Visit visit = new Visit();
        DiseaseHistoryId historyId = Objects.requireNonNull(converter.convert(source.getDiseaseHistory()));
        visit.setDiseaseHistory(new DiseaseHistory(historyId));
        visit.setWhen(source.getWhen());
        return visit;
    }


}
