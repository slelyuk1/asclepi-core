package com.jupiter.asclepi.core.service.impl.visit.converter;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.request.disease.visit.CreateVisitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

public class CreateVisitRequestConverter implements Converter<CreateVisitRequest, Visit> {

    // todo use converter to convert GetDiseaseHistoryRequest

    @Override
    public Visit convert(CreateVisitRequest source) {
        Visit visit = new Visit();
        Client client = new Client(source.getDiseaseHistory().getClientId());
        DiseaseHistory diseaseHistory = new DiseaseHistory(client, source.getDiseaseHistory().getNumber());
        visit.setDiseaseHistory(diseaseHistory);
        visit.setWhen(source.getWhen());
        return visit;
    }


}
