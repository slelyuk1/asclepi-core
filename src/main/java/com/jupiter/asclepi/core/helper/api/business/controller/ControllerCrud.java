package com.jupiter.asclepi.core.helper.api.business.controller;

import com.jupiter.asclepi.core.helper.api.business.shared.*;
import org.springframework.http.ResponseEntity;

public interface ControllerCrud<GetRequestType, CreateRequestType, EditRequestType, ResponseType> extends
        Getting<GetRequestType, ResponseEntity<ResponseType>>,
        GettingAll<ResponseType>,
        Creation<CreateRequestType, ResponseType>,
        Editing<EditRequestType, ResponseType>,
        Deletion<GetRequestType, ResponseEntity<?>> {
}
