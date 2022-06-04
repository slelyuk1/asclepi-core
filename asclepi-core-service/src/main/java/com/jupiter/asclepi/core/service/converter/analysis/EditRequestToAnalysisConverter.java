package com.jupiter.asclepi.core.service.converter.analysis;

import com.jupiter.asclepi.core.model.request.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.repository.entity.analysis.Analysis;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface EditRequestToAnalysisConverter extends Converter<EditAnalysisRequest, Analysis> {

    @Override
    @Mapping(target = "id", source = "analysis")
    @Mapping(target = "visit", ignore = true)
    @Mapping(target = "createdWhen", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Analysis convert(EditAnalysisRequest source);

}
