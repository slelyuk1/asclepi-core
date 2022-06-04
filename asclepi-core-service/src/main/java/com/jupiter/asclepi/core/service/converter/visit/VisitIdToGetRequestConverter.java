package com.jupiter.asclepi.core.service.converter.visit;


import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.repository.entity.visit.VisitId;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface VisitIdToGetRequestConverter extends Converter<VisitId, GetVisitRequest> {

    @Override
    @Mapping(target = "diseaseHistory", source = "diseaseHistoryId")
    GetVisitRequest convert(VisitId source);

}
