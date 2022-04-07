package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CrudService;
import com.jupiter.asclepi.core.model.entity.document.Document;
import com.jupiter.asclepi.core.model.request.disease.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.model.request.disease.document.EditDocumentRequest;

import java.math.BigInteger;

// todo implement service (Viktor Muratov) (see com.jupiter.asclepi.core.service.EmployeeService)
public interface DocumentService extends CrudService<BigInteger, CreateDocumentRequest, EditDocumentRequest, Document, Boolean> {
}
