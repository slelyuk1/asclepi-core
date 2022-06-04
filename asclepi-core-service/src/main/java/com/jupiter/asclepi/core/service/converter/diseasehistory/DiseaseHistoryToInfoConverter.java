package com.jupiter.asclepi.core.service.converter.diseasehistory;

import com.jupiter.asclepi.core.model.response.DiseaseHistoryInfo;
import com.jupiter.asclepi.core.repository.entity.Diagnosis;
import com.jupiter.asclepi.core.repository.entity.DiseaseHistory;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MappingConfiguration.class)
public interface DiseaseHistoryToInfoConverter extends Converter<DiseaseHistory, DiseaseHistoryInfo> {

    @Override
    @Mapping(target = "diseaseHistory", source = "id")
    @Mapping(target = "diagnosisIds", source = "diagnoses")
    @Mapping(target = "doctorId", source = "doctor.id")
    DiseaseHistoryInfo convert(DiseaseHistory source);

    @Nullable
    default Integer convertDiagnosisToId(@Nullable Diagnosis diagnosis) {
        return diagnosis != null ? diagnosis.getId().getNumber() : null;
    }

}
