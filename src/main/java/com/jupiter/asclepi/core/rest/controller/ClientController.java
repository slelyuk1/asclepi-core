package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.shared.Creation;
import com.jupiter.asclepi.core.helper.api.business.shared.Editing;
import com.jupiter.asclepi.core.helper.api.business.shared.Getting;
import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.people.EditClientRequest;
import com.jupiter.asclepi.core.model.response.people.ClientInfo;

public interface ClientController extends
        Getting<Integer, ClientInfo>,
        Creation<CreateClientRequest, ClientInfo>,
        Editing<EditClientRequest, ClientInfo> {
}
