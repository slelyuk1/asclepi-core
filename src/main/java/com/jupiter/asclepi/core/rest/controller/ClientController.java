package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CreateController;
import com.jupiter.asclepi.core.helper.api.business.controller.EditController;
import com.jupiter.asclepi.core.helper.api.business.controller.GetAllController;
import com.jupiter.asclepi.core.helper.api.business.controller.GetController;
import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.people.EditClientRequest;
import com.jupiter.asclepi.core.model.response.people.ClientInfo;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

// todo configure and implement REST controller (Dima Kuzmenko) (see com.jupiter.asclepi.core.rest.controller.EmployeeController)
public interface ClientController extends
        GetController<Integer, ClientInfo>,
        GetAllController<ClientInfo>,
        CreateController<CreateClientRequest>,
        EditController<EditClientRequest> {

    @Override
    ResponseEntity<?> create(@NotNull CreateClientRequest createRequest);

    @Override
    ResponseEntity<?> edit(@NotNull EditClientRequest editRequest);

    @Override
    List<ClientInfo> getAll();

    @Override
    ResponseEntity<ClientInfo> getOne(@NotNull Integer getRequest);
}
