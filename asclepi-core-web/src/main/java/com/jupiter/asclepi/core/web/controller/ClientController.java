package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.client.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.client.EditClientRequest;
import com.jupiter.asclepi.core.model.response.client.ClientInfo;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.web.helper.api.CreateController;
import com.jupiter.asclepi.core.web.helper.api.GetAllController;
import com.jupiter.asclepi.core.web.helper.api.GetController;
import com.jupiter.asclepi.core.web.helper.api.PatchController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.List;

public interface ClientController extends
        GetController<BigInteger, Client, BigInteger, ClientInfo>,
        GetAllController<ClientInfo, Client, BigInteger>,
        CreateController<CreateClientRequest, Client, BigInteger, ClientInfo>,
        PatchController<EditClientRequest, Client, BigInteger, ClientInfo> {

    @Override
    default Class<ClientInfo> getResponseClass() {
        return ClientInfo.class;
    }

    @Override
    default ClientInfo create(@RequestBody CreateClientRequest createRequest) {
        return CreateController.super.create(createRequest);
    }

    @Override
    default List<ClientInfo> getAll() {
        return GetAllController.super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    default ClientInfo getOne(@PathVariable("id") BigInteger getRequest) {
        return GetController.super.getOne(getRequest);
    }

    @Override
    default ClientInfo edit(@RequestBody EditClientRequest editRequest) {
        return PatchController.super.edit(editRequest);
    }
}
