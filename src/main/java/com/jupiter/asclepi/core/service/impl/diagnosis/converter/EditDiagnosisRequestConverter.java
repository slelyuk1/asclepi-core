package com.jupiter.asclepi.core.service.impl.diagnosis.converter;

import com.jupiter.asclepi.core.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import org.springframework.core.convert.converter.Converter;

public class EditDiagnosisRequestConverter implements Converter<EditDiagnosisRequest, Diagnosis> {
    @Override
    public Diagnosis convert(EditDiagnosisRequest source) {
        GetDiagnosisRequest diagnosisGetter = source.getDiagnosis();
        GetDiseaseHistoryRequest historyGetter = diagnosisGetter.getDiseaseHistory();

        DiseaseHistory history = new DiseaseHistory(new Client(historyGetter.getClientId()), historyGetter.getNumber());
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiseaseHistory(history);
        diagnosis.setNumber(diagnosis.getNumber());
        diagnosis.setComplications(source.getComplications());
        diagnosis.setDisease(source.getDisease());
        diagnosis.setEtiologyAndPathogenesis(source.getEtiologyAndPathogenesis());
        diagnosis.setSpecialityOfCourse(source.getSpecialityOfCourse());
        diagnosis.setIsFinal(source.getIsFinal());
        return diagnosis;
    }
}
