package com.jupiter.asclepi.core.service.converter.diagnosis;

import com.jupiter.asclepi.core.model.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.model.entity.disease.diagnosis.DiagnosisId;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;


@RequiredArgsConstructor
public class LegacyEditDiagnosisRequestConverter implements Converter<EditDiagnosisRequest, Diagnosis> {

    private final Converter<GetDiseaseHistoryRequest, DiseaseHistoryId> converter;

    @Override
    public Diagnosis convert(EditDiagnosisRequest source) {
        GetDiagnosisRequest diagnosisGetter = source.getDiagnosis();
        GetDiseaseHistoryRequest historyGetter = diagnosisGetter.getDiseaseHistory();

        DiseaseHistoryId diseaseHistoryId = Objects.requireNonNull(converter.convert(historyGetter));

        DiagnosisId diagnosisId = new DiagnosisId(diseaseHistoryId, diagnosisGetter.getNumber());
        Diagnosis diagnosis = new Diagnosis(diagnosisId);
        diagnosis.setComplications(source.getComplications());
        diagnosis.setDisease(source.getDisease());
        diagnosis.setEtiologyAndPathogenesis(source.getEtiologyAndPathogenesis());
        diagnosis.setSpecialityOfCourse(source.getSpecialityOfCourse());
        diagnosis.setIsFinal(source.getIsFinal());
        return diagnosis;
    }
}
