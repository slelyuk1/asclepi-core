package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.model.request.document.EditDocumentRequest;
import com.jupiter.asclepi.core.model.response.DocumentInfo;
import com.jupiter.asclepi.core.repository.entity.Document;
import com.jupiter.asclepi.core.service.api.DocumentService;
import com.jupiter.asclepi.core.web.controller.DocumentController;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/document")
public class DocumentControllerImpl implements DocumentController {

    private final DocumentService service;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<DocumentInfo> create(CreateDocumentRequest createRequest) {
        Document employee = service.create(createRequest);
        DocumentInfo employeeInfo = conversionService.convert(employee, DocumentInfo.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeInfo);
    }

    @Override
    public List<DocumentInfo> getAll() {
        return service.getAll().stream()
                .map(employee -> conversionService.convert(employee, DocumentInfo.class))
                .toList();
    }

    @Override
    public ResponseEntity<DocumentInfo> edit(EditDocumentRequest editRequest) {
        Document edited = service.edit(editRequest);
        DocumentInfo employeeInfo = conversionService.convert(edited, DocumentInfo.class);
        return ResponseEntity.ok().body(employeeInfo);
    }

    @Override
    public ResponseEntity<Void> delete(BigInteger toDeleteId) {
        boolean result = service.delete(toDeleteId);
        return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<DocumentInfo> getOne(BigInteger getRequest) {
        return service.getOne(getRequest)
                .map(document -> conversionService.convert(document, DocumentInfo.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
