package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.model.request.people.EditClientRequest;
import com.jupiter.asclepi.core.service.helper.api.CrudService;

import java.math.BigInteger;

public interface ClientService extends CrudService<BigInteger, CreateClientRequest, EditClientRequest, Client, Boolean> {
}
