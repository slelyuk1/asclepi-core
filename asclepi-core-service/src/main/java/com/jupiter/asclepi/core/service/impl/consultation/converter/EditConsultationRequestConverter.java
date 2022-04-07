package com.jupiter.asclepi.core.service.impl.consultation.converter;

import com.jupiter.asclepi.core.model.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.model.entity.disease.consultation.Consultation;
import com.jupiter.asclepi.core.model.model.entity.disease.consultation.ConsultationId;
import com.jupiter.asclepi.core.model.model.request.disease.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.model.model.request.disease.consultation.GetConsultationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

@RequiredArgsConstructor
public class EditConsultationRequestConverter implements Converter<EditConsultationRequest, Consultation> {

    private final Converter<GetConsultationRequest, ConsultationId> visitIdConverter;

    @Override
    public Consultation convert(EditConsultationRequest source) {
        ConsultationId id = Objects.requireNonNull(visitIdConverter.convert(source.getConsultation()));
        Consultation consultation = new Consultation(id);
        if(Objects.nonNull(source.getAnamnesisId())){
            consultation.setAnamnesis(new Anamnesis(source.getAnamnesisId()));
        }
        consultation.setInspection(source.getInspection());
        return consultation;
    }
}
