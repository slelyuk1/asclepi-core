package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.repository.VisitRepository;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.repository.entity.visit.VisitId;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.api.VisitService;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.service.util.CustomBeanUtils;
import com.jupiter.asclepi.core.service.util.IdGeneratorUtils;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
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
public class VisitServiceImpl implements VisitService {

    private final VisitRepository repository;
    private final DiseaseHistoryService diseaseHistoryService;
    private final ConversionService conversionService;

    @Override
    public Try<Visit> create(@Valid @NotNull CreateVisitRequest createRequest) {
        return Try.of(() -> {
            GetDiseaseHistoryRequest diseaseHistoryGetter = createRequest.getDiseaseHistory();
            Visit toCreate = Objects.requireNonNull(conversionService.convert(createRequest, Visit.class));
            DiseaseHistory diseaseHistory = diseaseHistoryService.getOne(diseaseHistoryGetter)
                    .orElseThrow(() -> new NonExistentIdException("Disease history", diseaseHistoryGetter));
            toCreate.setId(new VisitId(diseaseHistory.getId(), IdGeneratorUtils.generateId().intValue()));
            return repository.save(toCreate);
        });
    }

    @Override
    public Try<Visit> edit(@Valid @NotNull EditVisitRequest editRequest) {
        return Try.of(() -> {
            Visit toCopyTo = getOne(editRequest.getVisit())
                    .orElseThrow(() -> new NonExistentIdException("Disease history", editRequest.getVisit()));
            Visit toCopyFrom = Objects.requireNonNull(conversionService.convert(editRequest, Visit.class));
            CustomBeanUtils.copyPropertiesWithoutNull(toCopyFrom, toCopyTo);
            return repository.save(toCopyTo);
        });
    }

    @Override
    public List<Visit> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Visit> getOne(@Valid @NotNull GetVisitRequest getRequest) {
        VisitId id = Objects.requireNonNull(conversionService.convert(getRequest, VisitId.class));
        return repository.findById(id);
    }

    @Override
    public List<Visit> getForDiseaseHistory(DiseaseHistory diseaseHistory) {
        Visit toFind = new Visit();
        toFind.setDiseaseHistory(diseaseHistory);
        return repository.findAll(Example.of(toFind));
    }
}
