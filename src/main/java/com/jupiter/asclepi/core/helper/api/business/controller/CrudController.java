package com.jupiter.asclepi.core.helper.api.business.controller;

public interface CrudController<GetRequestType, CreateRequestType, EditRequestType, ResponseType> extends
        GetController<GetRequestType, ResponseType>,
        GetAllController<ResponseType>,
        CreateController<CreateRequestType>,
        EditController<EditRequestType>,
        DeleteController<GetRequestType> {
}
