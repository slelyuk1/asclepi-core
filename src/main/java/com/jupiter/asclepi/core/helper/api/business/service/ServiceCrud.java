package com.jupiter.asclepi.core.helper.api.business.service;

import com.jupiter.asclepi.core.helper.api.business.shared.*;

import java.util.Optional;

public interface ServiceCrud<GetRequestType, CreateRequestType, EditRequestType, ResponseType, DeleteResponseType> extends
        Getting<GetRequestType, Optional<ResponseType>>,
        GettingAll<ResponseType>,
        Creation<CreateRequestType, ResponseType>,
        Editing<EditRequestType, ResponseType>,
        Deletion<GetRequestType, DeleteResponseType> {
}
