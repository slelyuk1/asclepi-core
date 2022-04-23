package com.jupiter.asclepi.core.service.converter.diagnosis;

import com.jupiter.asclepi.core.model.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@SuppressWarnings("UnmappedTargetProperties")
@Mapper(config = MappingConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateRequestToDiagnosisConverter extends Converter<CreateDiagnosisRequest, Diagnosis> {

    @Override
    Diagnosis convert(@Nullable CreateDiagnosisRequest source);

}
