package com.jupiter.asclepi.core.service.converter.consultation;

import com.jupiter.asclepi.core.model.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.model.entity.disease.consultation.Consultation;
import com.jupiter.asclepi.core.model.model.response.disease.ConsultationInfo;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.math.BigInteger;

@Mapper(config = MappingConfiguration.class)
public interface ConsultationToInfoConverter extends Converter<Consultation, ConsultationInfo> {

    @Override
    @Mapping(target = "consultation", source = ".")
    @Mapping(target = "anamnesisId", source = "anamnesis")
    ConsultationInfo convert(@Nullable Consultation source);

    default BigInteger getIdFromAnamnesis(Anamnesis anamnesis) {
        return anamnesis != null ? anamnesis.getId() : null;
    }
}
