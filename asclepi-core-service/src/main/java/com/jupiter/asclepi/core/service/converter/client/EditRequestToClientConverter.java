package com.jupiter.asclepi.core.service.converter.client;

import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.request.people.EditClientRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.core.convert.converter.Converter;

@SuppressWarnings("UnmappedTargetProperties")
@Mapper(config = MappingConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EditRequestToClientConverter extends Converter<EditClientRequest, Client> {

    @Override
    Client convert(EditClientRequest request);

}
