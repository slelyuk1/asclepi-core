package com.jupiter.asclepi.core.service.converter.visit;

import com.jupiter.asclepi.core.model.request.visit.EditVisitRequest;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface EditRequestToVisitConverter extends Converter<EditVisitRequest, Visit> {

    @Override
    @Mapping(target = "id", source = "visit")
    @Mapping(target = "diseaseHistory", ignore = true)
    @Mapping(target = "createdWhen", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Visit convert(EditVisitRequest source);

}
