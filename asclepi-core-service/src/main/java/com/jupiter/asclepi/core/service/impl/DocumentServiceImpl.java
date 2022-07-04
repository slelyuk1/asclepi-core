package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.model.request.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.model.request.document.EditDocumentRequest;
import com.jupiter.asclepi.core.repository.entity.Document;
import com.jupiter.asclepi.core.repository.entity.analysis.Analysis;
import com.jupiter.asclepi.core.service.api.AnalysisService;
import com.jupiter.asclepi.core.service.api.DocumentService;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.service.helper.api.v2.AbstractService;
import com.jupiter.asclepi.core.service.util.CustomBeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Validated
public class DocumentServiceImpl extends AbstractService<Document, BigInteger> implements DocumentService {

    private final AnalysisService analysisService;

    public DocumentServiceImpl(ConversionService conversionService,
                               JpaRepository<Document, BigInteger> repository,
                               AnalysisService analysisService) {
        super(conversionService, repository);
        this.analysisService = analysisService;
    }

    @Override
    public void preProcessBeforeCreation(CreateDocumentRequest request, Document toCreate) {
        Analysis analysis = analysisService.getOne(request.getAnalysis())
                .orElseThrow(() -> new NonExistentIdException("Analysis", request.getAnalysis()));
        toCreate.setAnalysis(analysis);
    }

    @Override
    public Document edit(@Valid @NotNull EditDocumentRequest editRequest) {
        Document toCopyFrom = Objects.requireNonNull(getConversionService().convert(editRequest, Document.class));
        Document existing = getOne(editRequest.getId())
                .orElseThrow(() -> new NonExistentIdException("Document", editRequest.getId()));
        CustomBeanUtils.copyPropertiesWithoutNull(toCopyFrom, existing);
        return getRepository().save(existing);
    }

}
