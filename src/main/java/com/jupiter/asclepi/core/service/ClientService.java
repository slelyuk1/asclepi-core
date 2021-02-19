package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CrudService;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.people.EditClientRequest;

// todo implement service (Dima Kuzmenko) (see com.jupiter.asclepi.core.service.EmployeeService)
public interface ClientService extends CrudService<Integer, CreateClientRequest, EditClientRequest, Client, Boolean> {
}
