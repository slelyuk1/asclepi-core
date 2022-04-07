package com.jupiter.asclepi.core.service.impl.visit.converter;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.VisitId;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

@RequiredArgsConstructor
public class GetVisitRequestConverter implements Converter<GetVisitRequest, VisitId> {

    private final Converter<GetDiseaseHistoryRequest, DiseaseHistoryId> historyIdConverter;

    @Override
    public VisitId convert(GetVisitRequest source) {
        DiseaseHistoryId historyId = Objects.requireNonNull(historyIdConverter.convert(source.getDiseaseHistory()));
        return new VisitId(historyId, source.getNumber());
    }
}
