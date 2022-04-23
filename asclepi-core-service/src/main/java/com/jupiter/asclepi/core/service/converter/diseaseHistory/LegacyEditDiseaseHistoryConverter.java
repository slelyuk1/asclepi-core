package com.jupiter.asclepi.core.service.converter.diseaseHistory;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.model.request.disease.history.EditDiseaseHistoryRequest;
import org.springframework.core.convert.converter.Converter;

public class LegacyEditDiseaseHistoryConverter implements Converter<EditDiseaseHistoryRequest, DiseaseHistory> {
    @Override
    public DiseaseHistory convert(EditDiseaseHistoryRequest source) {
        DiseaseHistory history = new DiseaseHistory();
        Client client = new Client();
        client.setId(source.getDiseaseHistory().getClientId());
        history.setClient(client);
        history.setNumber(source.getDiseaseHistory().getNumber());

        Employee doctor = new Employee();
        doctor.setId(source.getDoctorId());
        history.setDoctor(doctor);

        return history;
    }
}
