package com.jupiter.asclepi.core.service.impl.analysis;

import com.jupiter.asclepi.core.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.model.entity.analysis.Analysis;
import com.jupiter.asclepi.core.model.entity.analysis.AnalysisId;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.request.disease.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.request.disease.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.request.disease.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.AnalysisRepository;
import com.jupiter.asclepi.core.service.AnalysisService;
import com.jupiter.asclepi.core.service.DiseaseHistoryService;
import com.jupiter.asclepi.core.util.CustomBeanUtils;
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
public class AnalysisServiceImpl implements AnalysisService {

    private final AnalysisRepository repository;
    private final DiseaseHistoryService diseaseHistoryService;
    private final ConversionService conversionService;

    @Override
    public Try<Analysis> create(@Valid @NotNull CreateAnalysisRequest createRequest) {
        return Try.of(() -> {
            GetDiseaseHistoryRequest diseaseHistoryGetter = createRequest.getVisit().getDiseaseHistory();

            diseaseHistoryService.getOne(diseaseHistoryGetter)
                    .orElseThrow(() -> new NonExistentIdException("Disease history", diseaseHistoryGetter));

            Analysis toCreate = Objects.requireNonNull(conversionService.convert(createRequest, Analysis.class));
            return repository.save(toCreate);
        });
    }

    @Override
    public Boolean delete(@Valid @NotNull GetAnalysisRequest deleteRequest) {
        return getOne(deleteRequest)
                .map(toDelete -> {
                    repository.delete(toDelete);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Try<Analysis> edit(@Valid @NotNull EditAnalysisRequest editRequest) {
        return Try.of(() -> {
            Analysis toCopyTo = getOne(editRequest.getAnalysis())
                    .orElseThrow(() -> new NonExistentIdException("Analysis", editRequest.getAnalysis()));
            Analysis toCopyFrom = Objects.requireNonNull(conversionService.convert(editRequest, Analysis.class));
            CustomBeanUtils.copyPropertiesWithoutNull(toCopyFrom, toCopyTo);
            return repository.save(toCopyTo);
        });
    }

    @Override
    public List<Analysis> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Analysis> getOne(@Valid @NotNull GetAnalysisRequest getRequest) {
        AnalysisId analysisId = Objects.requireNonNull(conversionService.convert(getRequest, AnalysisId.class));

        return repository.findById(analysisId);
    }

    @Override
    public List<Analysis> getForVisit(Visit getRequest) {
        Analysis toFind = new Analysis();
        toFind.setVisitNumber(getRequest.getNumber());
        return repository.findAll(Example.of(toFind));
    }

    @Override
    public List<Analysis> getForDiseaseHistory(DiseaseHistory history) {
        Analysis toFind = new Analysis();
        toFind.setDiseaseHistoryNumber(history.getNumber());
        return repository.findAll(Example.of(toFind));
    }
}
