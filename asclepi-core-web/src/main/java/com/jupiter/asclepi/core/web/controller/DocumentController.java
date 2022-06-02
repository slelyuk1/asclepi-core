package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.disease.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.model.request.disease.document.EditDocumentRequest;
import com.jupiter.asclepi.core.model.response.disease.DocumentInfo;
import com.jupiter.asclepi.core.web.helper.api.CrudController;

import java.math.BigInteger;

// todo configure and implement REST controller (Viktor Muratov) (see com.jupiter.asclepi.core.rest.controller.EmployeeController)
@SuppressWarnings("unused")
public interface DocumentController extends CrudController<BigInteger, CreateDocumentRequest, EditDocumentRequest, DocumentInfo> {
}
