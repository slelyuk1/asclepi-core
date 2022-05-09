package com.jupiter.asclepi.core.service.converter.analysis;

import com.jupiter.asclepi.core.model.model.entity.analysis.Analysis;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@SuppressWarnings("UnmappedTargetProperties")
@Mapper(config = MappingConfiguration.class)
public interface CreateRequestToAnalysisConverter extends Converter<CreateAnalysisRequest, Analysis> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "createdWhen", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Analysis convert(CreateAnalysisRequest source);

}