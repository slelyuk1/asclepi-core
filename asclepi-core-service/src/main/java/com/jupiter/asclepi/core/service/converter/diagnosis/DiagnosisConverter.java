package com.jupiter.asclepi.core.service.converter.diagnosis;

import com.jupiter.asclepi.core.model.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.response.disease.DiagnosisInfo;
import org.springframework.core.convert.converter.Converter;

public class DiagnosisConverter implements Converter<Diagnosis, DiagnosisInfo> {
    @Override
    public DiagnosisInfo convert(Diagnosis source) {
        DiseaseHistory history = source.getDiseaseHistory();

        GetDiseaseHistoryRequest historyGetter =
                new GetDiseaseHistoryRequest(history.getClient().getId(),
                        history.getNumber());

        GetDiagnosisRequest diagnosisGetter = new GetDiagnosisRequest(historyGetter, source.getNumber());
        return new DiagnosisInfo(diagnosisGetter, source.getDisease(), source.getComplications(),
                source.getEtiologyAndPathogenesis(), source.getSpecialityOfCourse(), source.getIsFinal());
    }
}
