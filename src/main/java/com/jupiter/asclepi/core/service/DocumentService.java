package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CrudService;
import com.jupiter.asclepi.core.model.entity.document.Document;
import com.jupiter.asclepi.core.model.request.disease.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.model.request.disease.document.EditDocumentRequest;
import io.vavr.control.Try;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

// todo implement service (Viktor Muratov) (see com.jupiter.asclepi.core.service.EmployeeService)
public interface DocumentService extends CrudService<BigInteger, CreateDocumentRequest, EditDocumentRequest, Document, Boolean> {
}
