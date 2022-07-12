package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.model.request.document.EditDocumentRequest;
import com.jupiter.asclepi.core.model.response.DocumentInfo;
import com.jupiter.asclepi.core.repository.entity.Document;
import com.jupiter.asclepi.core.service.api.DocumentService;
import com.jupiter.asclepi.core.service.helper.api.CrudService;
import com.jupiter.asclepi.core.web.controller.DocumentController;
import com.jupiter.asclepi.core.web.helper.api.AbstractController;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/v1/document")
public class DocumentControllerImpl extends AbstractController<DocumentInfo> implements DocumentController {

    private final DocumentService service;

    public DocumentControllerImpl(ConversionService conversionService, DocumentService service) {
        super(conversionService);
        this.service = service;
    }

    @Override
    public CrudService<BigInteger, CreateDocumentRequest, EditDocumentRequest, Document, BigInteger> getCrudService() {
        return service;
    }

}
