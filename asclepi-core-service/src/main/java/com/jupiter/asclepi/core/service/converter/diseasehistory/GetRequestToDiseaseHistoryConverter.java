package com.jupiter.asclepi.core.service.converter.diseasehistory;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface GetRequestToDiseaseHistoryConverter extends Converter<GetDiseaseHistoryRequest, DiseaseHistory> {

    @Override
    @Mapping(target = "client", source = ".")
    @Mapping(target = "number")
    DiseaseHistory convert( GetDiseaseHistoryRequest source);

    @SuppressWarnings("UnmappedTargetProperties")
    @Mapping(target = "id", source = "clientId")
    Client convertToClient(GetDiseaseHistoryRequest source);

}
