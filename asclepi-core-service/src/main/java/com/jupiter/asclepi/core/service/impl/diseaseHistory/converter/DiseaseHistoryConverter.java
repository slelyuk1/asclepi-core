package com.jupiter.asclepi.core.service.impl.diseaseHistory.converter;

import com.jupiter.asclepi.core.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.disease.DiseaseHistoryInfo;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.stream.Collectors;

public class DiseaseHistoryConverter implements Converter<DiseaseHistory, DiseaseHistoryInfo> {
    @Override
    public DiseaseHistoryInfo convert(DiseaseHistory source) {
        GetDiseaseHistoryRequest getter = new GetDiseaseHistoryRequest();
        getter.setClientId(source.getClient().getId());
        getter.setNumber(source.getNumber());
        List<Integer> diagnosisIds = source.getDiagnoses().stream()
                .map(Diagnosis::getNumber)
                .collect(Collectors.toList());
        return new DiseaseHistoryInfo(getter, diagnosisIds, source.getDoctor().getId());
    }
}
