package com.jupiter.asclepi.core.service.impl.anamnesis;

import com.jupiter.asclepi.core.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.AnamnesisRepository;
import com.jupiter.asclepi.core.service.AnamnesisService;
import com.jupiter.asclepi.core.service.DiseaseHistoryService;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnamnesisServiceImpl implements AnamnesisService {

    private final AnamnesisRepository repository;
    private final DiseaseHistoryService diseaseHistoryService;
    private final ConversionService conversionService;

    @Override
    public Try<Anamnesis> create(@Valid @NotNull CreateAnamnesisRequest createRequest) {
        return Try.of(() -> {
            GetDiseaseHistoryRequest historyGetter = createRequest.getDiseaseHistory();
            DiseaseHistory linkedHistory = diseaseHistoryService.getOne(historyGetter)
                    .orElseThrow(() -> new NonExistentIdException("Disease history", historyGetter));
            Anamnesis toCreate = Objects.requireNonNull(conversionService.convert(createRequest, Anamnesis.class));
            toCreate.setDiseaseHistory(linkedHistory);
            return repository.save(toCreate);
        });
    }

    @Override
    public Boolean delete(@Valid @NotNull BigInteger toDeleteId) {
        return repository.findById(toDeleteId)
                .map(toDelete -> {
                    repository.delete(toDelete);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<Anamnesis> getOne(@Valid @NotNull BigInteger getRequest) {
        return repository.findById(getRequest);
    }

    @Override
    public List<Anamnesis> getForDiseaseHistory(@Valid @NotNull DiseaseHistory history) {
        Anamnesis toFind = new Anamnesis();
        toFind.setDiseaseHistory(history);
        return repository.findAll(Example.of(toFind));
    }
}
