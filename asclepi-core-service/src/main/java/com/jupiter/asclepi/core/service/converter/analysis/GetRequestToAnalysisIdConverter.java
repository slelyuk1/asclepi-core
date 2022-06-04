package com.jupiter.asclepi.core.service.converter.analysis;

import com.jupiter.asclepi.core.model.request.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.repository.entity.analysis.AnalysisId;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface GetRequestToAnalysisIdConverter extends Converter<GetAnalysisRequest, AnalysisId> {

    @Override
    @Mapping(target = "visitId", source = "visit")
    AnalysisId convert(GetAnalysisRequest source);

}
