package com.jupiter.asclepi.core.service.impl.consultation.converter;

import com.jupiter.asclepi.core.model.entity.disease.consultation.ConsultationId;
import com.jupiter.asclepi.core.model.entity.disease.visit.VisitId;
import com.jupiter.asclepi.core.model.request.disease.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

@RequiredArgsConstructor
public class GetConsultationRequestConverter implements Converter<GetConsultationRequest, ConsultationId> {

    private final Converter<GetVisitRequest, VisitId> visitIdConverter;

    @Override
    public ConsultationId convert(GetConsultationRequest source) {
        VisitId visitId = Objects.requireNonNull(visitIdConverter.convert(source.getVisit()));
        return new ConsultationId(visitId, source.getNumber());
    }
}
