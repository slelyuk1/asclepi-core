package com.jupiter.asclepi.core.service.impl.client.converter;

import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.response.people.ClientInfo;
import org.springframework.core.convert.converter.Converter;

public class ClientConverter implements Converter<Client, ClientInfo> {
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