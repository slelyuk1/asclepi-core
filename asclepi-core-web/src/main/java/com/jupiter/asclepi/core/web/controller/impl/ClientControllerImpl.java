package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.client.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.client.EditClientRequest;
import com.jupiter.asclepi.core.model.response.client.ClientInfo;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.service.api.ClientService;
import com.jupiter.asclepi.core.web.controller.ClientController;
import com.jupiter.asclepi.core.web.helper.api.CreateController;
import com.jupiter.asclepi.core.web.helper.api.EditController;
import com.jupiter.asclepi.core.web.helper.api.GetAllController;
import com.jupiter.asclepi.core.web.helper.api.GetController;
import com.jupiter.asclepi.core.web.util.ControllerUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/client")
public class ClientControllerImpl implements ClientController {

    private final ClientService clientService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<ClientInfo> create(@NotNull CreateClientRequest createRequest) {
        Client client = clientService.create(createRequest);
        ClientInfo clientInfo = conversionService.convert(client, ClientInfo.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientInfo);
    }

    @Override
    public ResponseEntity<?> edit(@NotNull EditClientRequest editRequest) {
        Client edited = clientService.edit(editRequest);
        ClientInfo clientInfo = conversionService.convert(edited, ClientInfo.class);
        return ResponseEntity.ok().body(clientInfo);
    }

    @Override
    public List<ClientInfo> getAll() {
        return clientService.getAll().stream()
                .map(client -> conversionService.convert(client, ClientInfo.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ClientInfo> getOne(@NotNull BigInteger getRequest) {
        return clientService.getOne(getRequest)
                .map(client -> conversionService.convert(client, ClientInfo.class))
                .map(ResponseEntity::ok)
                .orElse(ControllerUtils.notFoundResponse());
    }
}
