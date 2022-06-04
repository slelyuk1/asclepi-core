package com.jupiter.asclepi.core.service.converter.diagnosis;

import com.jupiter.asclepi.core.model.response.DiagnosisInfo;
import com.jupiter.asclepi.core.repository.entity.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface DiagnosisToInfoConverter extends Converter<Diagnosis, DiagnosisInfo> {

    @Override
    @Mapping(target = "diagnosis", source = "id")
    DiagnosisInfo convert(Diagnosis source);

}
