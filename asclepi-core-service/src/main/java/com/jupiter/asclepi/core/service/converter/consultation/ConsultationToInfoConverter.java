package com.jupiter.asclepi.core.service.converter.consultation;

import com.jupiter.asclepi.core.model.response.ConsultationInfo;
import com.jupiter.asclepi.core.repository.entity.Consultation;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface ConsultationToInfoConverter extends Converter<Consultation, ConsultationInfo> {

    @Override
    @Mapping(target = "consultation", source = "id")
    @Mapping(target = "anamnesisId", source = "anamnesis.id")
    ConsultationInfo convert(Consultation source);

}
