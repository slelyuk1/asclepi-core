package com.jupiter.asclepi.core.service.converter.diagnosis;

import com.jupiter.asclepi.core.model.model.entity.disease.diagnosis.DiagnosisId;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface GetRequestToDiagnosisIdConverter extends Converter<GetDiagnosisRequest, DiagnosisId> {

    @Override
    @Mapping(target = "diseaseHistory")
    @Mapping(target = "number")
    DiagnosisId convert(GetDiagnosisRequest source);

}
