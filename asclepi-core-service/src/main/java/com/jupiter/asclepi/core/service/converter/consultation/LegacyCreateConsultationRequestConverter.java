package com.jupiter.asclepi.core.service.converter.consultation;

import com.jupiter.asclepi.core.model.model.entity.disease.consultation.Consultation;
import com.jupiter.asclepi.core.model.model.request.disease.consultation.CreateConsultationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@RequiredArgsConstructor
public class LegacyCreateConsultationRequestConverter implements Converter<CreateConsultationRequest, Consultation> {

    @Override
    public Consultation convert(CreateConsultationRequest source) {
        Consultation consultation = new Consultation();
        // todo set other fields
        consultation.setInspection(source.getInspection());
        return consultation;
    }
}
