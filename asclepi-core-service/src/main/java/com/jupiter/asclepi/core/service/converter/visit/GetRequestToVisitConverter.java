package com.jupiter.asclepi.core.service.converter.visit;

import com.jupiter.asclepi.core.model.model.entity.disease.visit.VisitId;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@SuppressWarnings("UnmappedTargetProperties")
@Mapper(config = MappingConfiguration.class)
public interface GetRequestToVisitConverter extends Converter<GetVisitRequest, VisitId> {

    @Override
    @Mapping(target = "diseaseHistory")
    VisitId convert(@Nullable GetVisitRequest source);

}
