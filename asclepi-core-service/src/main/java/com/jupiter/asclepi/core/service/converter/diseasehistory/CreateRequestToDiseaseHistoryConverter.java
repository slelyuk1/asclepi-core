package com.jupiter.asclepi.core.service.converter.diseasehistory;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.model.request.disease.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface CreateRequestToDiseaseHistoryConverter extends Converter<CreateDiseaseHistoryRequest, DiseaseHistory> {

    @Override
    @Mapping(target = "id", source = ".")
    @Mapping(target = "doctor", source = ".")
    @Mapping(target = "createdWhen", ignore = true)
    @Mapping(target = "creator", ignore = true)
    DiseaseHistory convert(CreateDiseaseHistoryRequest source);

    @Mapping(target = "client", source = "clientId")
    @Mapping(target = "number", ignore = true)
    DiseaseHistoryId convertToDiseaseHistoryId(CreateDiseaseHistoryRequest source);

    @Mapping(target = "id", source = "doctorId")
    Employee convertToDoctor(CreateDiseaseHistoryRequest source);

}
