package com.jupiter.asclepi.core.web.helper.api.crud;

import com.jupiter.asclepi.core.web.helper.api.CreateController;
import com.jupiter.asclepi.core.web.helper.api.EditController;
import com.jupiter.asclepi.core.web.helper.api.GetAllController;
import com.jupiter.asclepi.core.web.helper.api.get.GetUsingRequestBodyController;
import com.jupiter.asclepi.core.web.helper.api.delete.DeleteUsingRequestBodyController;

public interface CrudUsingRequestBodyController<GetRequestType, CreateRequestType, EditRequestType, ResponseType> extends
        GetUsingRequestBodyController<GetRequestType, ResponseType>,
        GetAllController<ResponseType>,
        CreateController<CreateRequestType, ResponseType>,
        EditController<EditRequestType>,
        DeleteUsingRequestBodyController<GetRequestType> {
}
