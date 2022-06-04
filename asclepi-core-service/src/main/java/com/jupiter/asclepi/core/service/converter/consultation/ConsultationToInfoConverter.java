package com.jupiter.asclepi.core.service.converter.consultation;

import com.jupiter.asclepi.core.repository.entity.Anamnesis;
import com.jupiter.asclepi.core.repository.entity.Consultation;
import com.jupiter.asclepi.core.model.response.ConsultationInfo;
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
    ConsultationInfo convert(Consultation source);

    @Nullable
    default BigInteger getIdFromAnamnesis(@Nullable Anamnesis anamnesis) {
        return anamnesis != null ? anamnesis.getId() : null;
    }
}
