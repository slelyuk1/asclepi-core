package com.jupiter.asclepi.core.service.converter.diseaseHistory;


import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.math.BigInteger;

@Mapper(config = MappingConfiguration.class)
public interface DiseaseHistoryToGetRequestConverter extends Converter<DiseaseHistory, GetDiseaseHistoryRequest> {

    @Override
    @Mapping(target = "number")
    @Mapping(target = "clientId", source = "client")
    GetDiseaseHistoryRequest convert( DiseaseHistory source);

    @Nullable
    default BigInteger getIdFromClient(@Nullable Client client) {
        return client != null ? client.getId() : null;
    }

}
