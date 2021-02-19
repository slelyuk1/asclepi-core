package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.service.CreateService;
import com.jupiter.asclepi.core.helper.api.business.service.EditService;
import com.jupiter.asclepi.core.helper.api.business.service.GetService;
import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.people.EditClientRequest;
import com.jupiter.asclepi.core.model.response.people.ClientInfo;

public interface ClientController extends
        GetService<Integer, ClientInfo>,
        CreateService<CreateClientRequest, ClientInfo>,
        EditService<EditClientRequest, ClientInfo> {
}
