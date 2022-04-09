package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CrudService;
import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.model.request.people.EditClientRequest;

import java.math.BigInteger;

public interface ClientService extends CrudService<BigInteger, CreateClientRequest, EditClientRequest, Client, Boolean> {
}
