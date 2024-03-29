package com.jupiter.asclepi.core.service.impl.diagnosis.converter;

import com.jupiter.asclepi.core.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.entity.disease.diagnosis.DiagnosisId;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.CreateVisitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

@RequiredArgsConstructor
public class CreateDiagnosisRequestConverter implements Converter<CreateDiagnosisRequest, Diagnosis> {

    private final Converter<GetDiseaseHistoryRequest, DiseaseHistoryId> converter;
    @Override
    public Diagnosis convert(CreateDiagnosisRequest source) {


        DiseaseHistoryId diseaseHistoryId = Objects.requireNonNull(converter.convert(source.getDiseaseHistory()));

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiseaseHistory(new DiseaseHistory(diseaseHistoryId));

        diagnosis.setComplications(source.getComplications());
        diagnosis.setDisease(source.getDisease());
        diagnosis.setEtiologyAndPathogenesis(source.getEtiologyAndPathogenesis());
        diagnosis.setSpecialityOfCourse(source.getSpecialityOfCourse());
        diagnosis.setIsFinal(source.getIsFinal());
        return diagnosis;
    }

}
