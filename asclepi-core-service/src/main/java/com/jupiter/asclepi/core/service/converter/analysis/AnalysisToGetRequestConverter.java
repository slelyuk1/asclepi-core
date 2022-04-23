package com.jupiter.asclepi.core.service.converter.analysis;


import com.jupiter.asclepi.core.model.model.entity.analysis.Analysis;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface AnalysisToGetRequestConverter extends Converter<Analysis, GetAnalysisRequest> {

    @Override
    @Mapping(target = "number")
    @Mapping(target = "visit")
    GetAnalysisRequest convert(Analysis source);

}
