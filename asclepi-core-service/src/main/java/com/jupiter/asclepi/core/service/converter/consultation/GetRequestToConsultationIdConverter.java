package com.jupiter.asclepi.core.service.converter.consultation;

import com.jupiter.asclepi.core.model.request.disease.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.repository.entity.id.ConsultationId;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface GetRequestToConsultationIdConverter extends Converter<GetConsultationRequest, ConsultationId> {

    @Override
    @Mapping(target = "visitId", source = "visit")
    ConsultationId convert(GetConsultationRequest source);

}
