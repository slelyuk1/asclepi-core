package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.model.request.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.repository.entity.Document;
import com.jupiter.asclepi.core.repository.entity.analysis.Analysis;
import com.jupiter.asclepi.core.service.api.AnalysisService;
import com.jupiter.asclepi.core.service.api.DocumentService;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.service.helper.api.AbstractService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigInteger;

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

}
