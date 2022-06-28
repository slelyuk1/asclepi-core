package com.jupiter.asclepi.core.service.converter.diseasehistory;

import com.jupiter.asclepi.core.model.request.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface CreateRequestToDiseaseHistoryConverter extends Converter<CreateDiseaseHistoryRequest, DiseaseHistory> {

    @Override
    @Mapping(target = "client.id", source = "clientId")
    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "creation", ignore = true)
    DiseaseHistory convert(CreateDiseaseHistoryRequest source);

}
