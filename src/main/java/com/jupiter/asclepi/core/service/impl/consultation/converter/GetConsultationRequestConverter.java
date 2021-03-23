package com.jupiter.asclepi.core.service.impl.consultation.converter;

import com.jupiter.asclepi.core.model.entity.disease.consultation.Consultation;
import com.jupiter.asclepi.core.model.entity.disease.consultation.ConsultationId;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.entity.disease.visit.VisitId;
import com.jupiter.asclepi.core.model.request.disease.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@RequiredArgsConstructor
public class GetConsultationRequestConverter implements Converter<GetConsultationRequest, ConsultationId> {

    private final Converter<GetVisitRequest, VisitId> visitIdConverter;

    @Override
    public ConsultationId convert(GetConsultationRequest source) {
        return new ConsultationId(visitIdConverter.convert(source.getVisit()), source.getNumber());
    }
}
