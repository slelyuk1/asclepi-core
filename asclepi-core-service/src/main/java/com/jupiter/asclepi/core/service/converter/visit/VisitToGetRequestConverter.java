package com.jupiter.asclepi.core.service.converter.visit;


import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface VisitToGetRequestConverter extends Converter<Visit, GetVisitRequest> {

    @Override
    @Mapping(target = "number")
    @Mapping(target = "diseaseHistory")
    GetVisitRequest convert( Visit source);

}
