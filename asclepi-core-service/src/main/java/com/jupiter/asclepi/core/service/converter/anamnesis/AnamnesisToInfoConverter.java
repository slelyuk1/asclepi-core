package com.jupiter.asclepi.core.service.converter.anamnesis;

import com.jupiter.asclepi.core.model.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.model.response.disease.AnamnesisInfo;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MappingConfiguration.class)
public interface AnamnesisToInfoConverter extends Converter<Anamnesis, AnamnesisInfo> {

    @Override
    AnamnesisInfo convert(@Nullable Anamnesis source);

}
