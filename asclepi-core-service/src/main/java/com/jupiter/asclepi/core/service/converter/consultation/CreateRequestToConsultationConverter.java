package com.jupiter.asclepi.core.service.converter.consultation;

import com.jupiter.asclepi.core.model.request.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.repository.entity.consultation.Consultation;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface CreateRequestToConsultationConverter extends Converter<CreateConsultationRequest, Consultation> {

    @Override
    @Mapping(target = "anamnesis.id", source = "anamnesisId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "anamnesis", ignore = true)
    @Mapping(target = "createdWhen", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Consultation convert(CreateConsultationRequest source);

}
