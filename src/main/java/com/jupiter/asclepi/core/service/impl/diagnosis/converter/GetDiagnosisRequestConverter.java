package com.jupiter.asclepi.core.service.impl.diagnosis.converter;

import com.jupiter.asclepi.core.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.entity.disease.diagnosis.DiagnosisId;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

@RequiredArgsConstructor
public class GetDiagnosisRequestConverter implements Converter<GetDiagnosisRequest, DiagnosisId> {

    private final Converter<GetDiseaseHistoryRequest, DiseaseHistoryId> historyIdConverter;

    @Override
    public DiagnosisId convert(GetDiagnosisRequest source) {
        DiseaseHistoryId historyId = Objects.requireNonNull(historyIdConverter.convert(source.getDiseaseHistory()));
        return new DiagnosisId(historyId, source.getNumber());
    }
}
