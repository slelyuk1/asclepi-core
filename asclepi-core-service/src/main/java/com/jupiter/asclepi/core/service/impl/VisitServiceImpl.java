package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.repository.entity.visit.VisitId;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.api.VisitService;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.service.helper.api.v2.AbstractService;
import com.jupiter.asclepi.core.service.util.CustomBeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public Visit edit(@Valid @NotNull EditVisitRequest editRequest) {
        Visit toCopyTo = getOne(editRequest.getVisit())
                .orElseThrow(() -> new NonExistentIdException("Disease history", editRequest.getVisit()));
        Visit toCopyFrom = Objects.requireNonNull(getConversionService().convert(editRequest, Visit.class));
        CustomBeanUtils.copyPropertiesWithoutNull(toCopyFrom, toCopyTo);
        return getRepository().save(toCopyTo);
    }

    @Override
    public Optional<Visit> getOne(@Valid @NotNull GetVisitRequest getRequest) {
        VisitId id = Objects.requireNonNull(getConversionService().convert(getRequest, VisitId.class));
        return getRepository().findById(id);
    }

    @Override
    public List<Visit> getForDiseaseHistory(DiseaseHistory diseaseHistory) {
        Visit toFind = new Visit();
        toFind.setDiseaseHistory(DiseaseHistory.fromId(diseaseHistory.getId()));
        return getRepository().findAll(Example.of(toFind));
    }

}
