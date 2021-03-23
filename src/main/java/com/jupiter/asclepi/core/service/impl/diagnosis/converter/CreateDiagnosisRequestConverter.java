package com.jupiter.asclepi.core.service.impl.diagnosis.converter;

import com.jupiter.asclepi.core.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.CreateVisitRequest;
import org.springframework.core.convert.converter.Converter;

public class CreateDiagnosisRequestConverter implements Converter<CreateDiagnosisRequest, Diagnosis> {

    @Override
    public Diagnosis convert(CreateDiagnosisRequest source) {
        Diagnosis diagnosis = new Diagnosis();
        Client client = new Client(source.getDiseaseHistory().getClientId());
        DiseaseHistory diseaseHistory = new DiseaseHistory(client, source.getDiseaseHistory().getNumber());
        diagnosis.setDiseaseHistory(diseaseHistory);
        diagnosis.setNumber(diagnosis.getNumber());
        diagnosis.setComplications(source.getComplications());
        diagnosis.setDisease(source.getDisease());
        diagnosis.setEtiologyAndPathogenesis(source.getEtiologyAndPathogenesis());
        diagnosis.setSpecialityOfCourse(source.getSpecialityOfCourse());
        diagnosis.setIsFinal(source.getIsFinal());
        return diagnosis;
    }

}
