package com.jupiter.asclepi.core.service.converter.client;

import com.jupiter.asclepi.core.model.response.ClientInfo;
import com.jupiter.asclepi.core.repository.entity.Client;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface ClientToInfoConverter extends Converter<Client, ClientInfo> {

    @Override
    ClientInfo convert(Client client);

}