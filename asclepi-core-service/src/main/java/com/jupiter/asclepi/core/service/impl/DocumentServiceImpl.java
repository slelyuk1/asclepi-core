package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.model.request.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.model.request.document.EditDocumentRequest;
import com.jupiter.asclepi.core.repository.DocumentRepository;
import com.jupiter.asclepi.core.repository.entity.Document;
import com.jupiter.asclepi.core.repository.entity.analysis.Analysis;
import com.jupiter.asclepi.core.service.api.AnalysisService;
import com.jupiter.asclepi.core.service.api.DocumentService;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.service.util.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Validated
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository repository;
    private final ConversionService conversionService;

    private final AnalysisService analysisService;

    @Override
    public Document create(@Valid @NotNull CreateDocumentRequest createRequest) {
        Document toCreate = Objects.requireNonNull(conversionService.convert(createRequest, Document.class));
        Analysis analysis = analysisService.getOne(createRequest.getAnalysis())
                .orElseThrow(() -> new NonExistentIdException("Analysis", createRequest.getAnalysis()));
        toCreate.setAnalysis(analysis);
        return repository.save(toCreate);
    }

    @Override
    public Boolean delete(@Valid @NotNull BigInteger toDeleteId) {
        return repository.findById(toDeleteId)
                .map(toDelete -> {
                    repository.delete(toDelete);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Document edit(@Valid @NotNull EditDocumentRequest editRequest) {
        Document toCopyFrom = Objects.requireNonNull(conversionService.convert(editRequest, Document.class));
        Document existing = getOne(editRequest.getId())
                .orElseThrow(() -> new NonExistentIdException("Document", editRequest.getId()));
        CustomBeanUtils.copyPropertiesWithoutNull(toCopyFrom, existing);
        return repository.save(existing);
    }

    @Override
    public List<Document> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Document> getOne(@Valid @NotNull BigInteger documentId) {
        return repository.findById(documentId);
    }

}
