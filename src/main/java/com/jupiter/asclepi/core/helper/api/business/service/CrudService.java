package com.jupiter.asclepi.core.helper.api.business.service;

import io.vavr.control.Try;

import java.util.Optional;

public interface CrudService<GetRequestType, CreateRequestType, EditRequestType, ResponseType, DeleteResponseType> extends
        GetService<GetRequestType, Optional<ResponseType>>,
        GetAllService<ResponseType>,
        CreateService<CreateRequestType, Try<ResponseType>>,
        EditService<EditRequestType, Try<ResponseType>>,
        DeleteService<GetRequestType, DeleteResponseType> {
}
