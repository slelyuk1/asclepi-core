package com.jupiter.asclepi.core.web.helper.api;

public interface CrudController<GetRequestType, CreateRequestType, EditRequestType, ResponseType> extends
        GetController<GetRequestType, ResponseType>,
        GetAllController<ResponseType>,
        CreateController<CreateRequestType, ResponseType>,
        EditController<EditRequestType>,
        DeleteController<GetRequestType> {
}
