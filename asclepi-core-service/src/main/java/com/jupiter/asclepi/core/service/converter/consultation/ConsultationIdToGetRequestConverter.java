package com.jupiter.asclepi.core.service.converter.consultation;

import com.jupiter.asclepi.core.model.request.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.repository.entity.consultation.ConsultationId;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface ConsultationIdToGetRequestConverter extends Converter<ConsultationId, GetConsultationRequest> {

    @Override
    @Mapping(target = "visit", source = "visitId")
    GetConsultationRequest convert(ConsultationId source);
}
