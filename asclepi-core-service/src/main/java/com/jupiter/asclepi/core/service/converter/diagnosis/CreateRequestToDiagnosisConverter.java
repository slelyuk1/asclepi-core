package com.jupiter.asclepi.core.service.converter.diagnosis;

import com.jupiter.asclepi.core.model.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface CreateRequestToDiagnosisConverter extends Converter<CreateDiagnosisRequest, Diagnosis> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "number", ignore = true)
    Diagnosis convert( CreateDiagnosisRequest source);

}
