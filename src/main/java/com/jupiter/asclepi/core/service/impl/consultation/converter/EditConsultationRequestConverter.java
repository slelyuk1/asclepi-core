package com.jupiter.asclepi.core.service.impl.consultation.converter;

import com.jupiter.asclepi.core.model.entity.disease.consultation.Consultation;
import com.jupiter.asclepi.core.model.request.disease.consultation.EditConsultationRequest;
import org.springframework.core.convert.converter.Converter;

public class EditConsultationRequestConverter implements Converter<EditConsultationRequest, Consultation> {
    @Override
    public Consultation convert(EditConsultationRequest source) {
        Consultation consultation = new Consultation();
        consultation.setInspection(source.getInspection());
        return consultation;
    }
}
