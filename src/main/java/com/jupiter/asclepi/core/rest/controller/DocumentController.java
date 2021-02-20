package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CrudController;
import com.jupiter.asclepi.core.model.response.disease.DocumentInfo;
import com.jupiter.asclepi.core.model.request.disease.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.model.request.disease.document.EditDocumentRequest;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

// todo configure and implement REST controller (Viktor Muratov) (see com.jupiter.asclepi.core.rest.controller.EmployeeController)
public interface DocumentController extends CrudController<BigInteger, CreateDocumentRequest, EditDocumentRequest, DocumentInfo> {
}
