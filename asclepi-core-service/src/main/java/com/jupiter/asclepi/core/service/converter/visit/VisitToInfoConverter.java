package com.jupiter.asclepi.core.service.converter.visit;

import com.jupiter.asclepi.core.model.response.VisitInfo;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface VisitToInfoConverter extends Converter<Visit, VisitInfo> {

    @Override
    @Mapping(target = "visit", source = "id")
    VisitInfo convert(Visit source);

}
