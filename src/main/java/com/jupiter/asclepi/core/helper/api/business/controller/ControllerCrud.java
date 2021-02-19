package com.jupiter.asclepi.core.helper.api.business.controller;

import com.jupiter.asclepi.core.helper.api.business.shared.*;
import org.springframework.http.ResponseEntity;

public interface ControllerCrud<GetRequestType, CreateRequestType, EditRequestType, ResponseType> extends
        Getting<GetRequestType, ResponseEntity<ResponseType>>,
        GettingAll<ResponseType>,
        Creation<CreateRequestType, ResponseEntity<?>>,
        Editing<EditRequestType, ResponseEntity<?>>,
        Deletion<GetRequestType, ResponseEntity<?>> {
}
