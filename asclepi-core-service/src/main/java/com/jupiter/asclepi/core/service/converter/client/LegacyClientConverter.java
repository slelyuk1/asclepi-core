package com.jupiter.asclepi.core.service.converter.client;

import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.response.people.ClientInfo;
import org.springframework.core.convert.converter.Converter;

public class LegacyClientConverter implements Converter<Client, ClientInfo> {
    @Override
    public ClientInfo convert(Client client) {
        return new ClientInfo(
                client.getId(),
                client.getName(),
                client.getSurname(),
                client.getMiddleName(),
                client.getResidence(),
                client.getGender(),
                client.getJob()
        );
    }
}