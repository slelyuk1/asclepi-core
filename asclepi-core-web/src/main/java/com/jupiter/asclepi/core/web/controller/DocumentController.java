package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.model.request.document.EditDocumentRequest;
import com.jupiter.asclepi.core.model.response.DocumentInfo;
import com.jupiter.asclepi.core.web.helper.api.crud.CrudUsingPathVariableController;

import java.math.BigInteger;

public interface DocumentController extends CrudUsingPathVariableController<BigInteger, CreateDocumentRequest, EditDocumentRequest, DocumentInfo> {
}
