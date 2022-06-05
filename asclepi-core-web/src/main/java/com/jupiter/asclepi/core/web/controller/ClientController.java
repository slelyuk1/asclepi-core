package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.client.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.client.EditClientRequest;
import com.jupiter.asclepi.core.model.response.client.ClientInfo;
import com.jupiter.asclepi.core.web.helper.api.CreateController;
import com.jupiter.asclepi.core.web.helper.api.EditController;
import com.jupiter.asclepi.core.web.helper.api.GetAllController;
import com.jupiter.asclepi.core.web.helper.api.get.GetUsingPathVariableController;
import com.jupiter.asclepi.core.web.helper.api.get.GetUsingRequestBodyController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

public interface ClientController extends
        GetUsingPathVariableController<BigInteger, ClientInfo>,
        GetAllController<ClientInfo>,
        CreateController<CreateClientRequest, ClientInfo>,
        EditController<EditClientRequest, ClientInfo> {

}
