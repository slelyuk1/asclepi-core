package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.model.request.client.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.client.EditClientRequest;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.service.helper.api.v2.CrudService;

import java.math.BigInteger;

public interface ClientService
        extends CrudService<BigInteger, CreateClientRequest, EditClientRequest, Client, BigInteger, Boolean> {

    @Override
    default Class<Client> getEntityClass(){
        return Client.class;
    }

    @Override
    default Class<BigInteger> getIdClass(){
        return BigInteger.class;
    }
}
