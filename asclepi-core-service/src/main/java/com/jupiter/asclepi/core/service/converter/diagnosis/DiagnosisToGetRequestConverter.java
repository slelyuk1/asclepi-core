package com.jupiter.asclepi.core.service.converter.diagnosis;

import com.jupiter.asclepi.core.model.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MappingConfiguration.class)
public interface DiagnosisToGetRequestConverter extends Converter<Diagnosis, GetDiagnosisRequest> {

    @Override
    GetDiagnosisRequest convert(@Nullable Diagnosis source);

}
