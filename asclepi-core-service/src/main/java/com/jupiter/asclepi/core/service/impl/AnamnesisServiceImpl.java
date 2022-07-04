package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.model.request.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.entity.Anamnesis;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.service.api.AnamnesisService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.service.helper.api.v2.AbstractService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class AnamnesisServiceImpl extends AbstractService<Anamnesis, BigInteger> implements AnamnesisService {

    private final DiseaseHistoryService diseaseHistoryService;

    public AnamnesisServiceImpl(ConversionService conversionService,
                                JpaRepository<Anamnesis, BigInteger> repository,
                                DiseaseHistoryService diseaseHistoryService) {
        super(conversionService, repository);
        this.diseaseHistoryService = diseaseHistoryService;
    }

    @Override
    public void preProcessBeforeCreation(CreateAnamnesisRequest request, Anamnesis toCreate) {
        GetDiseaseHistoryRequest historyGetter = request.getDiseaseHistory();
        DiseaseHistory linkedHistory = diseaseHistoryService.getOne(historyGetter)
                .orElseThrow(() -> new NonExistentIdException("Disease history", historyGetter));
        toCreate.setDiseaseHistory(linkedHistory);
    }

    @Override
    public Boolean delete(@Valid @NotNull BigInteger toDeleteId) {
        return getRepository().findById(toDeleteId)
                .map(toDelete -> {
                    getRepository().delete(toDelete);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<Anamnesis> getOne(@Valid @NotNull BigInteger getRequest) {
        return getRepository().findById(getRequest);
    }

    @Override
    public List<Anamnesis> getForDiseaseHistory(@Valid @NotNull DiseaseHistory history) {
        Anamnesis toFind = new Anamnesis();
        toFind.setDiseaseHistory(DiseaseHistory.fromId(history.getId()));
        return getRepository().findAll(Example.of(toFind));
    }
}
