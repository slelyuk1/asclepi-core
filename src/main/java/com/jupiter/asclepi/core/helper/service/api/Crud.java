package com.jupiter.asclepi.core.helper.service.api;

public interface Crud<GetRequestType, CreateRequestType, EditRequestType, ResponseType> extends
        Getting<GetRequestType, ResponseType>,
        Creation<CreateRequestType, ResponseType>,
        Editing<EditRequestType, ResponseType>,
        Deletion<GetRequestType> {
}
