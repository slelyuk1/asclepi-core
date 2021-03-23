package com.jupiter.asclepi.core.service.impl.diagnosis.converter;

import com.jupiter.asclepi.core.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.disease.DiagnosisInfo;
import org.springframework.core.convert.converter.Converter;

public class DiagnosisConverter implements Converter<Diagnosis, DiagnosisInfo> {
    @Override
    public DiagnosisInfo convert(Diagnosis source) {
        GetDiagnosisRequest diagnosisGetter = new GetDiagnosisRequest();
        diagnosisGetter.setNumber(source.getNumber());
        GetDiseaseHistoryRequest historyGetter = new GetDiseaseHistoryRequest();
        historyGetter.setClientId(source.getDiseaseHistory().getClient().getId());
        historyGetter.setNumber(source.getDiseaseHistory().getNumber());
        diagnosisGetter.setDiseaseHistory(historyGetter);
        return new DiagnosisInfo(diagnosisGetter, source.getDisease(), source.getComplications(),
                source.getEtiologyAndPathogenesis(), source.getSpecialityOfCourse(), source.getIsFinal());
    }
}
