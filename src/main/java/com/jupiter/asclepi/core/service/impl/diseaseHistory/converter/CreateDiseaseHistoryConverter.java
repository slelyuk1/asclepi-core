package com.jupiter.asclepi.core.service.impl.diseaseHistory.converter;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.request.disease.history.CreateDiseaseHistoryRequest;
import org.springframework.core.convert.converter.Converter;

public class CreateDiseaseHistoryConverter implements Converter<CreateDiseaseHistoryRequest, DiseaseHistory> {
    @Override
    public DiseaseHistory convert(CreateDiseaseHistoryRequest source) {
        DiseaseHistory history = new DiseaseHistory();

        history.setClientId(source.getClientId());

        Employee doctor = new Employee();
        doctor.setId(source.getDoctorId());
        history.setDoctor(doctor);

        return history;
    }
}
