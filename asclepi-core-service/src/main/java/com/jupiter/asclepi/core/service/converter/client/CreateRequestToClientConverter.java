package com.jupiter.asclepi.core.service.converter.client;

import com.jupiter.asclepi.core.repository.entity.Client;
import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.core.convert.converter.Converter;

@SuppressWarnings("UnmappedTargetProperties")
@Mapper(config = MappingConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateRequestToClientConverter extends Converter<CreateClientRequest, Client> {

    @Override
    Client convert(CreateClientRequest request);

}
