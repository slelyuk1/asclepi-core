package com.jupiter.asclepi.core.web.helper.api.crud;

import com.jupiter.asclepi.core.web.helper.api.CreateController;
import com.jupiter.asclepi.core.web.helper.api.EditController;
import com.jupiter.asclepi.core.web.helper.api.GetAllController;
import com.jupiter.asclepi.core.web.helper.api.get.GetUsingPathVariableController;
import com.jupiter.asclepi.core.web.helper.api.get.GetUsingRequestBodyController;
import com.jupiter.asclepi.core.web.helper.api.delete.DeleteUsingPathVariableController;

public interface CrudUsingPathVariableController<GetRequestType, CreateRequestType, EditRequestType, ResponseType> extends
        GetUsingPathVariableController<GetRequestType, ResponseType>,
        GetAllController<ResponseType>,
        CreateController<CreateRequestType, ResponseType>,
        EditController<EditRequestType, ResponseType>,
        DeleteUsingPathVariableController<GetRequestType> {
}
