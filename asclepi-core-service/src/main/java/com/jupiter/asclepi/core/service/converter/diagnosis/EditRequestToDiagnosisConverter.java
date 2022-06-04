package com.jupiter.asclepi.core.service.converter.diagnosis;

import com.jupiter.asclepi.core.model.request.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.repository.entity.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;


@Mapper(config = MappingConfiguration.class)
public interface EditRequestToDiagnosisConverter extends Converter<EditDiagnosisRequest, Diagnosis> {

    @Override
    @Mapping(target = "id", source = "diagnosis")
    @Mapping(target = "diseaseHistory", ignore = true)
    Diagnosis convert(EditDiagnosisRequest source);

}
