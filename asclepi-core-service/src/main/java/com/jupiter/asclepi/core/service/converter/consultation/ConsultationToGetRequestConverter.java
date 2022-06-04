package com.jupiter.asclepi.core.service.converter.consultation;

import com.jupiter.asclepi.core.model.request.disease.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.repository.entity.Consultation;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

// todo id to getrequest
@Mapper(config = MappingConfiguration.class)
public interface ConsultationToGetRequestConverter extends Converter<Consultation, GetConsultationRequest> {

    @Override
    @Mapping(target = "number", source = "id.number")
    GetConsultationRequest convert(Consultation source);
}
