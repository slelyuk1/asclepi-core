package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.model.request.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.repository.entity.analysis.Analysis;
import com.jupiter.asclepi.core.repository.entity.analysis.AnalysisId;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.repository.entity.visit.VisitId;
import com.jupiter.asclepi.core.service.api.AnalysisService;
import com.jupiter.asclepi.core.service.api.VisitService;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.service.helper.api.AbstractService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Transactional
@Validated
@Service
public class AnalysisServiceImpl extends AbstractService<Analysis, AnalysisId> implements AnalysisService {

    private final VisitService visitService;

    public AnalysisServiceImpl(ConversionService conversionService,
                               JpaRepository<Analysis, AnalysisId> repository,
                               VisitService visitService) {
        super(conversionService, repository);
        this.visitService = visitService;
    }

    @Override
    public void preProcessBeforeCreation(CreateAnalysisRequest request, Analysis toCreate) {
        GetVisitRequest visitGetter = request.getVisit();
        Visit visit = visitService.getOne(visitGetter)
                .orElseThrow(() -> new NonExistentIdException("Visit", visitGetter));
        toCreate.setId(new AnalysisId(visit.getId(), (int) getRepository().count()));
    }

    @Override
    public List<Analysis> getForVisit(@NotNull Visit getRequest) {
        Analysis toFind = new Analysis();
        toFind.setVisit(Visit.fromId(getRequest.getId()));
        return getRepository().findAll(Example.of(toFind));
    }

    @Override
    public List<Analysis> getForDiseaseHistory(@NotNull DiseaseHistory history) {
        Analysis toFind = new Analysis();
        toFind.setVisit(Visit.fromId(new VisitId(history.getId(), null)));
        return getRepository().findAll(Example.of(toFind));
    }
}
