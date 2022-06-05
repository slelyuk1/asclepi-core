package com.jupiter.asclepi.core.web.helper.api.crud;

import com.jupiter.asclepi.core.web.helper.api.CreateController;
import com.jupiter.asclepi.core.web.helper.api.EditController;
import com.jupiter.asclepi.core.web.helper.api.GetAllController;
import com.jupiter.asclepi.core.web.helper.api.GetController;
import com.jupiter.asclepi.core.web.helper.api.delete.DeleteUsingBodyController;

public interface CrudUsingBodyController<GetRequestType, CreateRequestType, EditRequestType, ResponseType> extends
        GetController<GetRequestType, ResponseType>,
        GetAllController<ResponseType>,
        CreateController<CreateRequestType, ResponseType>,
        EditController<EditRequestType>,
        DeleteUsingBodyController<GetRequestType> {
}
