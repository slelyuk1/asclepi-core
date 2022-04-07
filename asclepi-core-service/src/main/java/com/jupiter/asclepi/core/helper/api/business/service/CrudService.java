package com.jupiter.asclepi.core.helper.api.business.service;

public interface CrudService<GetRequestType, CreateRequestType, EditRequestType, ResponseType, DeleteResponseType> extends
        GetService<GetRequestType, ResponseType>,
        GetAllService<ResponseType>,
        CreateService<CreateRequestType, ResponseType>,
        EditService<EditRequestType, ResponseType>,
        DeleteService<GetRequestType, DeleteResponseType> {
}
