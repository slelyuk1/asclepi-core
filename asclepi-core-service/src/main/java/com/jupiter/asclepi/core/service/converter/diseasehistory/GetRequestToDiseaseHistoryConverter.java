package com.jupiter.asclepi.core.service.converter.diseasehistory;

import com.jupiter.asclepi.core.repository.entity.DiseaseHistory;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface GetRequestToDiseaseHistoryConverter extends Converter<GetDiseaseHistoryRequest, DiseaseHistory> {

    @Override
    @Mapping(target = "id", source = ".")
    DiseaseHistory convert(GetDiseaseHistoryRequest source);

}
