package com.jupiter.asclepi.core.service.converter.diagnosis;

import com.jupiter.asclepi.core.model.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;


@SuppressWarnings("UnmappedTargetProperties")
@Mapper(config = MappingConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EditRequestToDiagnosisConverter extends Converter<EditDiagnosisRequest, Diagnosis> {

    @Override
    @Mapping(target = "id", source = "diagnosis")
    Diagnosis convert(@Nullable EditDiagnosisRequest source);

}
