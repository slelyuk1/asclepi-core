package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.people.EditClientRequest;
import com.jupiter.asclepi.core.repository.entity.Client;
import com.jupiter.asclepi.core.service.helper.api.CrudService;

import java.math.BigInteger;

public interface ClientService extends CrudService<BigInteger, CreateClientRequest, EditClientRequest, Client, Boolean> {
}
