package com.jupiter.asclepi.core.service.converter.diseasehistory;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface GetRequestToDiseaseHistoryIdConverter extends Converter<GetDiseaseHistoryRequest, DiseaseHistoryId> {

    @Override
    @Mapping(target = "client", source = "clientId")
    DiseaseHistoryId convert(GetDiseaseHistoryRequest source);

}
