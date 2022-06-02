package com.jupiter.asclepi.core.service.converter.consultation;

import com.jupiter.asclepi.core.repository.entity.Consultation;
import com.jupiter.asclepi.core.model.request.disease.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface ConsultationToGetRequestConverter extends Converter<Consultation, GetConsultationRequest> {

    @Override
    GetConsultationRequest convert(Consultation source);
}
