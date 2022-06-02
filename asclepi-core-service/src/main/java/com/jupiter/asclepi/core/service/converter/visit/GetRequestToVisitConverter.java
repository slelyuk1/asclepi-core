package com.jupiter.asclepi.core.service.converter.visit;

import com.jupiter.asclepi.core.repository.entity.id.VisitId;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface GetRequestToVisitConverter extends Converter<GetVisitRequest, VisitId> {

    @Override
    @Mapping(target = "diseaseHistoryId", source = "diseaseHistory")
    VisitId convert(GetVisitRequest source);

}
