package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.client.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.client.EditClientRequest;
import com.jupiter.asclepi.core.model.response.client.ClientInfo;
import com.jupiter.asclepi.core.web.helper.api.CreateController;
import com.jupiter.asclepi.core.web.helper.api.GetAllController;
import com.jupiter.asclepi.core.web.helper.api.PatchController;
import com.jupiter.asclepi.core.web.helper.api.get.GetUsingPathVariableController;

import java.math.BigInteger;

public interface ClientController extends
        GetUsingPathVariableController<BigInteger, ClientInfo>,
        GetAllController<ClientInfo>,
        CreateController<CreateClientRequest, ClientInfo>,
        PatchController<EditClientRequest, ClientInfo> {

}
