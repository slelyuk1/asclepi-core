package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.service.api.Crud;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.people.EditClientRequest;

public interface ClientService extends Crud<Integer, CreateClientRequest, EditClientRequest, Client> {
}
