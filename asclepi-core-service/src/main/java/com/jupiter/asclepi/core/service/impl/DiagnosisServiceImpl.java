package com.jupiter.asclepi.core.service.impl;


import com.jupiter.asclepi.core.model.request.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.entity.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.repository.entity.diagnosis.DiagnosisId;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.service.api.DiagnosisService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.service.helper.api.v2.AbstractService;
import org.springframework.beans.BeanUtils;
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
public class DiagnosisServiceImpl extends AbstractService<Diagnosis, DiagnosisId> implements DiagnosisService {

    private final DiseaseHistoryService diseaseHistoryService;

    public DiagnosisServiceImpl(ConversionService conversionService,
                                JpaRepository<Diagnosis, DiagnosisId> repository,
                                DiseaseHistoryService diseaseHistoryService) {
        super(conversionService, repository);
        this.diseaseHistoryService = diseaseHistoryService;
    }

    @Override
    public void preProcessBeforeCreation(CreateDiagnosisRequest request, Diagnosis toCreate) {
        GetDiseaseHistoryRequest diseaseHistoryGetter = request.getDiseaseHistory();
        DiseaseHistory diseaseHistory = diseaseHistoryService.getOne(diseaseHistoryGetter)
                .orElseThrow(() -> new NonExistentIdException("Disease history", diseaseHistoryGetter));
        toCreate.setId(new DiagnosisId(diseaseHistory.getId(), (int) getRepository().count()));
    }

    @Override
    public Boolean delete(@Valid @NotNull GetDiagnosisRequest deleteRequest) {
        return getOne(deleteRequest)
                .map(toDelete -> {
                    getRepository().delete(toDelete);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Diagnosis edit(@Valid @NotNull EditDiagnosisRequest editRequest) {
        Diagnosis toCopyTo = getOne(editRequest.getDiagnosis())
                .orElseThrow(() -> new NonExistentIdException("Disease history", editRequest.getDiagnosis()));
        Diagnosis toCopyFrom = Objects.requireNonNull(getConversionService().convert(editRequest, Diagnosis.class));
        BeanUtils.copyProperties(toCopyFrom, toCopyTo);
        return getRepository().save(toCopyTo);
    }

    @Override
    public Optional<Diagnosis> getOne(@Valid @NotNull GetDiagnosisRequest getRequest) {
        DiagnosisId toFind = Objects.requireNonNull(getConversionService().convert(getRequest, DiagnosisId.class));
        return getRepository().findById(toFind);
    }

    @Override
    public List<Diagnosis> getForDiseaseHistory(@NotNull DiseaseHistory diseaseHistory) {
        Diagnosis toFind = new Diagnosis();
        toFind.setDiseaseHistory(DiseaseHistory.fromId(diseaseHistory.getId()));
        return getRepository().findAll(Example.of(toFind));
    }
}
