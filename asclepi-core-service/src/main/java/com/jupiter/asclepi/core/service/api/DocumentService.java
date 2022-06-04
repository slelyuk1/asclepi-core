package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.model.request.disease.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.model.request.disease.document.EditDocumentRequest;
import com.jupiter.asclepi.core.repository.entity.Document;
import com.jupiter.asclepi.core.service.helper.api.CrudService;

import java.math.BigInteger;

// todo implement service (Viktor Muratov) (see com.jupiter.asclepi.core.service.api.EmployeeService)
@SuppressWarnings("unused")
public interface DocumentService extends CrudService<BigInteger, CreateDocumentRequest, EditDocumentRequest, Document, Boolean> {
}
