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
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.NotNull;
import java.util.List;

// done
// todo configure and implement REST controller (Dima Kuzmenko) (see com.jupiter.asclepi.core.rest.controller.EmployeeController)
public interface ClientController extends
        GetController<Integer, ClientInfo>,
        GetAllController<ClientInfo>,
        CreateController<CreateClientRequest>,
        EditController<EditClientRequest> {

    @PostMapping("/create")
    @Override
    ResponseEntity<?> create(@NotNull CreateClientRequest createRequest);

    @Override
    @PostMapping("/edit")
    ResponseEntity<?> edit(@NotNull EditClientRequest editRequest);

    @Override
    @GetMapping("/all")
    List<ClientInfo> getAll();

    @Override
    @GetMapping("/")
    ResponseEntity<ClientInfo> getOne(@NotNull Integer getRequest);
}
