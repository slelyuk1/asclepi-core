package com.jupiter.asclepi.core.service.converter.visit;

import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.request.disease.visit.EditVisitRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@SuppressWarnings("UnmappedTargetProperties")
@Mapper(config = MappingConfiguration.class)
public interface EditRequestToVisitConverter extends Converter<EditVisitRequest, Visit> {

    @Override
    @Mapping(target = "id", source = "visit")
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "createdWhen", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Visit convert(EditVisitRequest source);

}