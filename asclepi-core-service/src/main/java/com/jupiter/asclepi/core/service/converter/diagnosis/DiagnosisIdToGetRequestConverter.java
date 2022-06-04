package com.jupiter.asclepi.core.service.converter.diagnosis;

import com.jupiter.asclepi.core.model.request.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.repository.entity.diagnosis.DiagnosisId;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface DiagnosisIdToGetRequestConverter extends Converter<DiagnosisId, GetDiagnosisRequest> {

    @Override
    @Mapping(target = "diseaseHistory", source = "diseaseHistoryId")
    GetDiagnosisRequest convert(DiagnosisId source);

}
