package com.jupiter.asclepi.core.service.impl.client.converter;

import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.request.people.EditClientRequest;
import org.springframework.core.convert.converter.Converter;

public class EditClientRequestConverter implements Converter<EditClientRequest, Client> {

    @Override
    public Client convert(EditClientRequest request) {
        Client client = new Client();
        client.setId(request.getId());
        client.setName(request.getName());
        client.setSurname(request.getSurname());
        client.setMiddleName(request.getMiddleName());
        client.setResidence(request.getResidence());
        client.setJob(request.getJob());
        client.setGender(request.getGender());
        return client;
    }
}
