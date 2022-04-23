package com.jupiter.asclepi.core.service.converter.anamnesis;

import com.jupiter.asclepi.core.model.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.model.request.disease.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MappingConfiguration.class)
public interface CreateRequestToAnamnesisConverter extends Converter<CreateAnamnesisRequest, Anamnesis> {

    @Override
    @Mapping(target = "id", ignore = true)
    Anamnesis convert(@Nullable CreateAnamnesisRequest source);

}
