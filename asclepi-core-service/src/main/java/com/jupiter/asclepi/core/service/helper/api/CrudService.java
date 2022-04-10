package com.jupiter.asclepi.core.service.helper.api;

public interface CrudService<GetRequestType, CreateRequestType, EditRequestType, ResponseType, DeleteResponseType> extends
        GetService<GetRequestType, ResponseType>,
        GetAllService<ResponseType>,
        CreateService<CreateRequestType, ResponseType>,
        EditService<EditRequestType, ResponseType>,
        DeleteService<GetRequestType, DeleteResponseType> {
}
