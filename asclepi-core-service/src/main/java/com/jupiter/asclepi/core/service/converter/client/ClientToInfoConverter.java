package com.jupiter.asclepi.core.service.converter.client;

import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.response.people.ClientInfo;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MappingConfiguration.class)
public interface ClientToInfoConverter extends Converter<Client, ClientInfo> {

    @Override
    ClientInfo convert(@Nullable Client client);

}