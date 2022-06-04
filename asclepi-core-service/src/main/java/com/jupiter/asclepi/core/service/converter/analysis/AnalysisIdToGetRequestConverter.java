package com.jupiter.asclepi.core.service.converter.analysis;


import com.jupiter.asclepi.core.model.request.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.repository.entity.analysis.AnalysisId;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface AnalysisIdToGetRequestConverter extends Converter<AnalysisId, GetAnalysisRequest> {

    @Override
    @Mapping(target = "visit", source = "visitId")
    GetAnalysisRequest convert(AnalysisId source);

}
