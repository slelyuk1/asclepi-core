package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.client.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.client.EditClientRequest;
import com.jupiter.asclepi.core.model.response.client.ClientInfo;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.service.api.ClientService;
import com.jupiter.asclepi.core.service.helper.api.CreateService;
import com.jupiter.asclepi.core.service.helper.api.EditService;
import com.jupiter.asclepi.core.service.helper.api.GetAllService;
import com.jupiter.asclepi.core.service.helper.api.GetService;
import com.jupiter.asclepi.core.web.controller.ClientController;
import com.jupiter.asclepi.core.web.helper.api.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@Slf4j
@RestController
@RequestMapping("/api/v1/client")
public class ClientControllerImpl extends AbstractController<ClientInfo> implements ClientController {

    private final ClientService clientService;

    public ClientControllerImpl(ConversionService conversionService, ClientService clientService) {
        super(conversionService);
        this.clientService = clientService;
    }

    @Override
    public CreateService<CreateClientRequest, Client, BigInteger> getServiceForCreation() {
        return clientService;
    }

    @Override
    public GetAllService<Client, BigInteger> getServiceForGetAll() {
        return clientService;
    }

    @Override
    public EditService<EditClientRequest, Client, BigInteger> getServiceForPatch() {
        return clientService;
    }

    @Override
    public GetService<BigInteger, Client, BigInteger> getServiceForGet() {
        return clientService;
    }
}
