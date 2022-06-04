package com.jupiter.asclepi.core.service.converter.diagnosis;

import com.jupiter.asclepi.core.model.request.disease.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.repository.entity.Diagnosis;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface DiagnosisToGetRequestConverter extends Converter<Diagnosis, GetDiagnosisRequest> {

    @Override
    @Mapping(target = "number", source = "id.number")
    GetDiagnosisRequest convert(Diagnosis source);

}
