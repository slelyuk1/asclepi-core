package com.jupiter.asclepi.core.service.converter.analysis;

import com.jupiter.asclepi.core.model.model.entity.analysis.AnalysisId;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@SuppressWarnings("UnmappedTargetProperties")
@Mapper(config = MappingConfiguration.class)
public interface GetRequestToAnalysisIdConverter extends Converter<GetAnalysisRequest, AnalysisId> {

    @Override
    @Mapping(target = "visitId", source = "visit")
    AnalysisId convert( GetAnalysisRequest source);

}
