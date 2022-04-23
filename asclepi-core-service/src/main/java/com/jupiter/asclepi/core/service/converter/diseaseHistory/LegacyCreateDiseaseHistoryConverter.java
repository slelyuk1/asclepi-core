package com.jupiter.asclepi.core.service.converter.diseaseHistory;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.model.request.disease.history.CreateDiseaseHistoryRequest;
import org.springframework.core.convert.converter.Converter;

public class LegacyCreateDiseaseHistoryConverter implements Converter<CreateDiseaseHistoryRequest, DiseaseHistory> {
    @Override
    public DiseaseHistory convert(CreateDiseaseHistoryRequest source) {
        DiseaseHistory history = new DiseaseHistory();

        Client client = new Client();
        client.setId(source.getClientId());
        history.setClient(client);

        Employee doctor = new Employee();
        doctor.setId(source.getDoctorId());
        history.setDoctor(doctor);

        return history;
    }
}
