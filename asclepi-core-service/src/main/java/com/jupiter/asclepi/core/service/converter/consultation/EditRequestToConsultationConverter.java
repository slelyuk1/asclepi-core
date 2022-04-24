package com.jupiter.asclepi.core.service.converter.consultation;

import com.jupiter.asclepi.core.model.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.model.entity.disease.consultation.Consultation;
import com.jupiter.asclepi.core.model.model.request.disease.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.math.BigInteger;

@SuppressWarnings("UnmappedTargetProperties")
@Mapper(config = MappingConfiguration.class)
public interface EditRequestToConsultationConverter extends Converter<EditConsultationRequest, Consultation> {

    @Override
    @Mapping(target = "id", source = "consultation")
    @Mapping(target = "anamnesis", source = "anamnesisId")
    @Mapping(target = "createdWhen", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Consultation convert(@Nullable EditConsultationRequest source);

    default Anamnesis convertToAnamnesis(BigInteger anamnesisId) {
        if (anamnesisId == null) {
            return null;
        }

        Anamnesis anamnesis = new Anamnesis();
        anamnesis.setId(anamnesisId);
        return anamnesis;
    }

}
