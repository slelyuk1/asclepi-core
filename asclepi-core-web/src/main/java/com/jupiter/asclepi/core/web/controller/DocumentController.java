package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.model.request.document.EditDocumentRequest;
import com.jupiter.asclepi.core.model.response.DocumentInfo;
import com.jupiter.asclepi.core.repository.entity.Document;
import com.jupiter.asclepi.core.web.helper.api.CrudController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.List;

public interface DocumentController extends CrudController<Document, BigInteger,BigInteger, CreateDocumentRequest, EditDocumentRequest, DocumentInfo> {

    @Override
    default Class<DocumentInfo> getResponseClass() {
        return DocumentInfo.class;
    }

    @Override
    default DocumentInfo create(@RequestBody CreateDocumentRequest createRequest) {
        return CrudController.super.create(createRequest);
    }

    @Override
    @DeleteMapping("/{id}")
    default void delete(@PathVariable("id") BigInteger deleteRequest) {
        CrudController.super.delete(deleteRequest);
    }

    @Override
    default List<DocumentInfo> getAll() {
        return CrudController.super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    default DocumentInfo getOne(@PathVariable("id") BigInteger getRequest) {
        return CrudController.super.getOne(getRequest);
    }

    @Override
    default DocumentInfo edit(@RequestBody EditDocumentRequest editRequest) {
        return CrudController.super.edit(editRequest);
    }
}
