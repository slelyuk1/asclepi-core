package com.jupiter.asclepi.core.service.converter.diagnosis;

import com.jupiter.asclepi.core.model.request.disease.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.repository.entity.id.DiagnosisId;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface GetRequestToDiagnosisIdConverter extends Converter<GetDiagnosisRequest, DiagnosisId> {

    @Override
    @Mapping(target = "diseaseHistoryId", source = "diseaseHistory")
    DiagnosisId convert(GetDiagnosisRequest source);

}
