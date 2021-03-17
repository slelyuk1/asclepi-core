package com.jupiter.asclepi.core.service.impl.diseaseHistory.converter;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import org.springframework.core.convert.converter.Converter;

public class GetDiseaseHistoryRequestConverter implements Converter<GetDiseaseHistoryRequest, DiseaseHistoryId> {
    @Override
    public DiseaseHistoryId convert(GetDiseaseHistoryRequest source) {
        DiseaseHistoryId id = new DiseaseHistoryId();
        id.setClientId(source.getClientId());
        id.setNumber(source.getNumber());
        return id;
    }
}
