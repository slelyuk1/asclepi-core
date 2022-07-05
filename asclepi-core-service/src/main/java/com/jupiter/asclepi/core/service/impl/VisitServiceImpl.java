package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.repository.entity.visit.VisitId;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.api.VisitService;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.service.helper.api.AbstractService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Validated
@Service
public class VisitServiceImpl extends AbstractService<Visit, VisitId> implements VisitService {

    private final DiseaseHistoryService diseaseHistoryService;

    public VisitServiceImpl(ConversionService conversionService,
                            JpaRepository<Visit, VisitId> repository,
                            DiseaseHistoryService diseaseHistoryService) {
        super(conversionService, repository);
        this.diseaseHistoryService = diseaseHistoryService;
    }

    @Override
    public void preProcessBeforeCreation(CreateVisitRequest request, Visit toCreate) {
        GetDiseaseHistoryRequest diseaseHistoryGetter = request.getDiseaseHistory();
        DiseaseHistory diseaseHistory = diseaseHistoryService.getOne(diseaseHistoryGetter)
                .orElseThrow(() -> new NonExistentIdException("Disease history", diseaseHistoryGetter));
        toCreate.setId(new VisitId(diseaseHistory.getId(), (int) getRepository().count()));
    }

    @Override
    public List<Visit> getForDiseaseHistory(DiseaseHistory diseaseHistory) {
        Visit toFind = new Visit();
        toFind.setDiseaseHistory(DiseaseHistory.fromId(diseaseHistory.getId()));
        return getRepository().findAll(Example.of(toFind));
    }

}
