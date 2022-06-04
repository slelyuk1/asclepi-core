package com.jupiter.asclepi.core.service.converter.consultation;

import com.jupiter.asclepi.core.model.request.disease.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.repository.entity.Consultation;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface EditRequestToConsultationConverter extends Converter<EditConsultationRequest, Consultation> {

    @Override
    @Mapping(target = "id", source = "consultation")
    @Mapping(target = "anamnesis.id", source = "anamnesisId")
    @Mapping(target = "anamnesis", ignore = true)
    @Mapping(target = "visit", ignore = true)
    @Mapping(target = "createdWhen", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Consultation convert(EditConsultationRequest source);

}
