package com.jupiter.asclepi.core.service.converter.diseasehistory;

import com.jupiter.asclepi.core.model.request.disease.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.entity.DiseaseHistory;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface EditRequestToDiseaseHistoryConverter extends Converter<EditDiseaseHistoryRequest, DiseaseHistory> {

    @Override
    @Mapping(target = "id", source = "diseaseHistory")
    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "createdWhen", ignore = true)
    @Mapping(target = "creator", ignore = true)
    DiseaseHistory convert(EditDiseaseHistoryRequest source);

}
