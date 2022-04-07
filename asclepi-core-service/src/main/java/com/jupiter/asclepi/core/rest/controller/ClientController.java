package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CreateController;
import com.jupiter.asclepi.core.helper.api.business.controller.EditController;
import com.jupiter.asclepi.core.helper.api.business.controller.GetAllController;
import com.jupiter.asclepi.core.helper.api.business.controller.GetController;
import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.people.EditClientRequest;
import com.jupiter.asclepi.core.model.response.people.ClientInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

public interface ClientController extends
        GetController<BigInteger, ClientInfo>,
        GetAllController<ClientInfo>,
        CreateController<CreateClientRequest>,
        EditController<EditClientRequest> {

    @PostMapping("/create")
    @Override
    ResponseEntity<?> create(@NotNull @RequestBody CreateClientRequest createRequest);

    @Override
    @PostMapping("/edit")
    ResponseEntity<?> edit(@NotNull @RequestBody EditClientRequest editRequest);

    @Override
    @GetMapping("/all")
    List<ClientInfo> getAll();

    @Override
    @GetMapping("/{clientId}")
    ResponseEntity<ClientInfo> getOne(@NotNull @PathVariable("clientId") BigInteger getRequest);
}
