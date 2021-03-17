package com.jupiter.asclepi.core.service.impl.diseaseHistory.converter;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.request.disease.history.EditDiseaseHistoryRequest;
import org.springframework.core.convert.converter.Converter;

public class EditDiseaseHistoryConverter implements Converter<EditDiseaseHistoryRequest, DiseaseHistory> {
    @Override
    public DiseaseHistory convert(EditDiseaseHistoryRequest source) {
        DiseaseHistory history = new DiseaseHistory();
        history.setClientId(source.getDiseaseHistory().getClientId());
        history.setNumber(source.getDiseaseHistory().getNumber());

        Employee doctor = new Employee();
        doctor.setId(source.getDoctorId());
        history.setDoctor(doctor);

        return history;
    }
}
