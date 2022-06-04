package com.jupiter.asclepi.core.service.converter.diseasehistory;


import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.entity.id.DiseaseHistoryId;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface DiseaseHistoryIdToGetRequestConverter extends Converter<DiseaseHistoryId, GetDiseaseHistoryRequest> {

    @Override
    GetDiseaseHistoryRequest convert(DiseaseHistoryId source);

}