package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.model.request.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.model.request.document.EditDocumentRequest;
import com.jupiter.asclepi.core.repository.entity.Document;
import com.jupiter.asclepi.core.service.helper.api.CrudService;

import java.math.BigInteger;

public interface DocumentService extends CrudService<BigInteger, CreateDocumentRequest, EditDocumentRequest, Document, BigInteger> {

    @Override
    default Class<Document> getEntityClass() {
        return Document.class;
    }

    @Override
    default Class<BigInteger> getIdClass() {
        return BigInteger.class;
    }
}
