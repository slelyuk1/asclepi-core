package com.jupiter.asclepi.core.service.converter.visit;

import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.request.disease.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MappingConfiguration.class)
public interface CreateRequestToVisitConverter extends Converter<CreateVisitRequest, Visit> {

    @Override
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "createdWhen", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Visit convert(@Nullable CreateVisitRequest source);

}
