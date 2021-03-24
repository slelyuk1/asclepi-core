package com.jupiter.asclepi.core.service.impl.diagnosis;


import com.jupiter.asclepi.core.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.DiagnosisRepository;
import com.jupiter.asclepi.core.service.DiagnosisService;
import com.jupiter.asclepi.core.service.DiseaseHistoryService;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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

    @Override
    public Try<Diagnosis> create(@Valid @NotNull CreateDiagnosisRequest createRequest) {
        return Try.of(() -> {
            GetDiseaseHistoryRequest diseaseHistoryGetter = createRequest.getDiseaseHistory();
            diseaseHistoryService.getOne(diseaseHistoryGetter)
                    .orElseThrow(() -> new NonExistentIdException("Disease history", diseaseHistoryGetter));
            Diagnosis toCreate = Objects.requireNonNull(conversionService.convert(createRequest, Diagnosis.class));
            return repository.save(toCreate);
        });
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
    public Try<Diagnosis> edit(@Valid @NotNull EditDiagnosisRequest editRequest) {
        return Try.of(() -> {
            Diagnosis toCopyTo = getOne(editRequest.getDiagnosis())
                    .orElseThrow(() -> new NonExistentIdException("Disease history", editRequest.getDiagnosis()));
            Diagnosis toCopyFrom = Objects.requireNonNull(conversionService.convert(editRequest, Diagnosis.class));
            BeanUtils.copyProperties(toCopyFrom, toCopyTo);
            return repository.save(toCopyTo);
        });
    }

    @Override
    public List<Diagnosis> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Diagnosis> getOne(@Valid @NotNull GetDiagnosisRequest getRequest) {
        Diagnosis toFind = Objects.requireNonNull(conversionService.convert(getRequest, Diagnosis.class));
        return repository.findOne(Example.of(toFind));
    }

    @Override
    public List<Diagnosis> getForDiseaseHistory(DiseaseHistory diseaseHistory) {
        Diagnosis toFind = new Diagnosis();
        toFind.setDiseaseHistory(diseaseHistory);
        return repository.findAll(Example.of(toFind));
    }
}
