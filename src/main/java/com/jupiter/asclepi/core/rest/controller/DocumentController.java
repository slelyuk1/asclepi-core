package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CrudController;
import com.jupiter.asclepi.core.model.response.disease.DocumentInfo;
import com.jupiter.asclepi.core.model.request.disease.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.model.request.disease.document.EditDocumentRequest;

import java.math.BigInteger;

public interface DocumentController extends CrudController<BigInteger, CreateDocumentRequest, EditDocumentRequest, DocumentInfo> {
}
