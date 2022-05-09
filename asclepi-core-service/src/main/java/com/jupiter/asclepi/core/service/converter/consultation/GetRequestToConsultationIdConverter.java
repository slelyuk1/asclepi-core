package com.jupiter.asclepi.core.service.converter.consultation;

import com.jupiter.asclepi.core.model.model.entity.disease.consultation.ConsultationId;
import com.jupiter.asclepi.core.model.model.request.disease.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@SuppressWarnings("UnmappedTargetProperties")
@Mapper(config = MappingConfiguration.class)
public interface GetRequestToConsultationIdConverter extends Converter<GetConsultationRequest, ConsultationId> {

    @Override
    @Mapping(target = "visitId", source = "visit")
    ConsultationId convert(GetConsultationRequest source);

}
