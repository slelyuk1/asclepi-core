package com.jupiter.asclepi.core.service.converter.analysis;

import com.jupiter.asclepi.core.model.response.AnalysisInfo;
import com.jupiter.asclepi.core.repository.entity.Analysis;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface AnalysisToInfoConverter extends Converter<Analysis, AnalysisInfo> {

    @Override
    @Mapping(target = "analysis", source = ".")
    @Mapping(target = "title")
    @Mapping(target = "summary")
    AnalysisInfo convert(Analysis source);

}
