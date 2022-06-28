package com.jupiter.asclepi.core.service.converter.analysis;

import com.jupiter.asclepi.core.model.request.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.repository.entity.analysis.Analysis;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface CreateRequestToAnalysisConverter extends Converter<CreateAnalysisRequest, Analysis> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creation", ignore = true)
    Analysis convert(CreateAnalysisRequest source);

}
