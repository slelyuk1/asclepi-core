package com.jupiter.asclepi.core.service.converter.diagnosis;

import com.jupiter.asclepi.core.repository.entity.Diagnosis;
import com.jupiter.asclepi.core.model.response.disease.DiagnosisInfo;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface DiagnosisToInfoConverter extends Converter<Diagnosis, DiagnosisInfo> {

    @Override
    @Mapping(target = "diagnosis", source = ".")
    DiagnosisInfo convert(Diagnosis source);

}
