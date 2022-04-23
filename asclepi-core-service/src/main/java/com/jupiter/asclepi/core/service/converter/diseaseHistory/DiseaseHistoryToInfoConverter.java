package com.jupiter.asclepi.core.service.converter.diseaseHistory;

import com.jupiter.asclepi.core.model.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.model.response.disease.DiseaseHistoryInfo;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MappingConfiguration.class)
public interface DiseaseHistoryToInfoConverter extends Converter<DiseaseHistory, DiseaseHistoryInfo> {

    @Override
    @Mapping(target = "diseaseHistory", source = ".")
    @Mapping(target = "diagnosisIds", source = "diagnoses")
    @Mapping(target = "doctorId", source = "doctor")
    DiseaseHistoryInfo convert(@Nullable DiseaseHistory source);

    default Integer convertDiagnosisToId(Diagnosis diagnosis) {
        return diagnosis != null ? diagnosis.getNumber() : null;
    }

    default Integer convertDoctorToId(Employee doctor) {
        return doctor != null ? doctor.getId() : null;
    }

}
