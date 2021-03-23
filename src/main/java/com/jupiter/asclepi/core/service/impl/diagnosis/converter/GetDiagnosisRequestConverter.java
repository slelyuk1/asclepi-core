package com.jupiter.asclepi.core.service.impl.diagnosis.converter;

import com.jupiter.asclepi.core.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import org.springframework.core.convert.converter.Converter;

public class GetDiagnosisRequestConverter implements Converter<GetDiagnosisRequest, Diagnosis> {
    @Override
    public Diagnosis convert(GetDiagnosisRequest source) {
        GetDiseaseHistoryRequest historyGetter = source.getDiseaseHistory();
        DiseaseHistory history = new DiseaseHistory(new Client(historyGetter.getClientId()), historyGetter.getNumber());
        // TODO return proper (Dima)
        return null;
    }
}
