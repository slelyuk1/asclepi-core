package com.jupiter.asclepi.core.service.converter.anamnesis;

import com.jupiter.asclepi.core.repository.entity.Anamnesis;
import com.jupiter.asclepi.core.model.response.disease.AnamnesisInfo;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface AnamnesisToInfoConverter extends Converter<Anamnesis, AnamnesisInfo> {

    @Override
    AnamnesisInfo convert(Anamnesis source);

}
