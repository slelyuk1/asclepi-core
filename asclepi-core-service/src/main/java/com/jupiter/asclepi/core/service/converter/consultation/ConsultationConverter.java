package com.jupiter.asclepi.core.service.converter.consultation;

import com.jupiter.asclepi.core.model.model.entity.disease.consultation.Consultation;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.request.disease.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.model.response.disease.ConsultationInfo;
import org.springframework.core.convert.converter.Converter;

public class ConsultationConverter implements Converter<Consultation, ConsultationInfo> {

    @Override
    public ConsultationInfo convert(Consultation source) {
        DiseaseHistory history = source.getAnamnesis().getDiseaseHistory();
        GetConsultationRequest consultationGetter = new GetConsultationRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getNumber()),
                        source.getVisit().getNumber()
                ),
                source.getNumber()
        );
        return new ConsultationInfo(consultationGetter, source.getAnamnesis().getId(), source.getInspection());
    }
}
