package com.jupiter.asclepi.core.service.converter.client;

import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.request.people.CreateClientRequest;
import org.springframework.core.convert.converter.Converter;

public class LegacyCreateClientRequestConverter implements Converter<CreateClientRequest, Client> {
    @Override
    public Client convert(CreateClientRequest request) {
        Client client = new Client();
        client.setName(request.getName());
        client.setSurname(request.getSurname());
        client.setMiddleName(request.getMiddleName());
        client.setResidence(request.getResidence());
        client.setJob(request.getJob());
        client.setGender(request.getGender());
        return client;
    }
}
