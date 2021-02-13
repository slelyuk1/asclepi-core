package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.ServiceCrud;
import com.jupiter.asclepi.core.model.entity.document.Document;
import com.jupiter.asclepi.core.model.request.disease.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.model.request.disease.document.EditDocumentRequest;

import java.math.BigInteger;

public interface DocumentService extends ServiceCrud<BigInteger, CreateDocumentRequest, EditDocumentRequest, Document, Void> {
}
