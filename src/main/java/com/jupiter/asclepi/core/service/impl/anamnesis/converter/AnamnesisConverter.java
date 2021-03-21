package com.jupiter.asclepi.core.service.impl.anamnesis.converter;

import com.jupiter.asclepi.core.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.disease.AnamnesisInfo;
import org.springframework.core.convert.converter.Converter;

public class AnamnesisConverter implements Converter<Anamnesis, AnamnesisInfo> {
    @Override
    public AnamnesisInfo convert(Anamnesis source) {
        DiseaseHistory history = source.getDiseaseHistory();
        // todo maybe use converter for history
        GetDiseaseHistoryRequest historyGetter = new GetDiseaseHistoryRequest(history.getClient().getId(), history.getNumber());
        return new AnamnesisInfo(source.getId(), historyGetter, source.getComplaints(), source.getVitae(), source.getMorbi());
    }
}
