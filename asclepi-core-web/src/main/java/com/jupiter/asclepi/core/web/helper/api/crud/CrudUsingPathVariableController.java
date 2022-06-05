package com.jupiter.asclepi.core.web.helper.api.crud;

import com.jupiter.asclepi.core.web.helper.api.CreateController;
import com.jupiter.asclepi.core.web.helper.api.GetAllController;
import com.jupiter.asclepi.core.web.helper.api.PatchController;
import com.jupiter.asclepi.core.web.helper.api.delete.DeleteUsingPathVariableController;
import com.jupiter.asclepi.core.web.helper.api.get.GetUsingPathVariableController;

public interface CrudUsingPathVariableController<GetRequestType, CreateRequestType, EditRequestType, ResponseType> extends
        GetUsingPathVariableController<GetRequestType, ResponseType>,
        GetAllController<ResponseType>,
        CreateController<CreateRequestType, ResponseType>,
        PatchController<EditRequestType, ResponseType>,
        DeleteUsingPathVariableController<GetRequestType> {
}
