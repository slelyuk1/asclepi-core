package com.jupiter.asclepi.core.service.converter.consultation;

import com.jupiter.asclepi.core.repository.entity.Anamnesis;
import com.jupiter.asclepi.core.repository.entity.Consultation;
import com.jupiter.asclepi.core.model.request.disease.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface CreateRequestToConsultationConverter extends Converter<CreateConsultationRequest, Consultation> {

    @Override
    @Mapping(target = "anamnesis", source = ".")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdWhen", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Consultation convert(CreateConsultationRequest source);

    @SuppressWarnings("UnmappedTargetProperties")
    @Mapping(target = "id", source = "anamnesisId")
    Anamnesis convertToAnamnesis(CreateConsultationRequest id);

}
