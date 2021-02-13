package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.ServiceCrud;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.people.EditClientRequest;

public interface ClientService extends ServiceCrud<Integer, CreateClientRequest, EditClientRequest, Client, Void> {
}
