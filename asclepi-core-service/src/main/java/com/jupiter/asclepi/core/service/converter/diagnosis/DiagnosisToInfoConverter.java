package com.jupiter.asclepi.core.service.converter.diagnosis;

import com.jupiter.asclepi.core.model.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.model.response.disease.DiagnosisInfo;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MappingConfiguration.class)
public interface DiagnosisToInfoConverter extends Converter<Diagnosis, DiagnosisInfo> {

    @Override
    @Mapping(target = "diagnosis", source = ".")
    DiagnosisInfo convert(@Nullable Diagnosis source);

}
