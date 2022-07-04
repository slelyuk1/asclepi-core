package com.jupiter.asclepi.core.service.converter.client;

import com.jupiter.asclepi.core.model.request.client.EditClientRequest;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EditRequestToClientConverter extends Converter<EditClientRequest, Client> {

    @Override
    @Mapping(target = "creation", expression = "java(null)")
    Client convert(EditClientRequest request);

}
