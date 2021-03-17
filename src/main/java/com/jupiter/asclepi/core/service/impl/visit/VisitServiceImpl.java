package com.jupiter.asclepi.core.service.impl.visit;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.request.disease.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.repository.VisitRepository;
import com.jupiter.asclepi.core.service.VisitService;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository repository;

    @Override
    public Try<Visit> create(@Valid @NotNull CreateVisitRequest createRequest) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Try<Visit> edit(@Valid @NotNull EditVisitRequest editRequest) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Visit> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Visit> getOne(@Valid @NotNull GetVisitRequest getRequest) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Visit> getByDiseaseHistory(DiseaseHistory diseaseHistory) {
        throw new UnsupportedOperationException();
    }
}
