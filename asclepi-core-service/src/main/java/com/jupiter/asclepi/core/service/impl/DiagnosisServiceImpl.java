package com.jupiter.asclepi.core.service.impl;


import com.jupiter.asclepi.core.model.request.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.DiagnosisRepository;
import com.jupiter.asclepi.core.repository.entity.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.repository.entity.diagnosis.DiagnosisId;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.service.api.DiagnosisService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Validated
@Service
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository repository;
    private final DiseaseHistoryService diseaseHistoryService;
    private final ConversionService conversionService;

    @Transactional
    @Override
    public Diagnosis create(@Valid @NotNull CreateDiagnosisRequest createRequest) {
        GetDiseaseHistoryRequest diseaseHistoryGetter = createRequest.getDiseaseHistory();
        DiseaseHistory diseaseHistory = diseaseHistoryService.getOne(diseaseHistoryGetter)
                .orElseThrow(() -> new NonExistentIdException("Disease history", diseaseHistoryGetter));
        Diagnosis toCreate = Objects.requireNonNull(conversionService.convert(createRequest, Diagnosis.class));
        toCreate.setId(new DiagnosisId(diseaseHistory.getId(), (int) repository.count()));
        return repository.save(toCreate);
    }

    @Override
    public Boolean delete(@Valid @NotNull GetDiagnosisRequest deleteRequest) {
        return getOne(deleteRequest)
                .map(toDelete -> {
                    repository.delete(toDelete);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Diagnosis edit(@Valid @NotNull EditDiagnosisRequest editRequest) {
        Diagnosis toCopyTo = getOne(editRequest.getDiagnosis())
                .orElseThrow(() -> new NonExistentIdException("Disease history", editRequest.getDiagnosis()));
        Diagnosis toCopyFrom = Objects.requireNonNull(conversionService.convert(editRequest, Diagnosis.class));
        BeanUtils.copyProperties(toCopyFrom, toCopyTo);
        return repository.save(toCopyTo);
    }

    @Override
    public List<Diagnosis> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Diagnosis> getOne(@Valid @NotNull GetDiagnosisRequest getRequest) {
        DiagnosisId toFind = Objects.requireNonNull(conversionService.convert(getRequest, DiagnosisId.class));
        return repository.findById(toFind);
    }

    @Override
    public List<Diagnosis> getForDiseaseHistory(@NotNull DiseaseHistory diseaseHistory) {
        Diagnosis toFind = new Diagnosis();
        toFind.setDiseaseHistory(diseaseHistory);
        return repository.findAll(Example.of(toFind));
    }
}
