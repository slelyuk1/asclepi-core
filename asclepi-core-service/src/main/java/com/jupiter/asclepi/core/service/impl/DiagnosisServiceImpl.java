package com.jupiter.asclepi.core.service.impl;


import com.jupiter.asclepi.core.model.request.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.entity.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.repository.entity.diagnosis.DiagnosisId;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.service.api.DiagnosisService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.service.helper.api.AbstractService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    public List<Diagnosis> getForDiseaseHistory(@NotNull DiseaseHistory diseaseHistory) {
        Diagnosis toFind = new Diagnosis();
        toFind.setDiseaseHistory(DiseaseHistory.fromId(diseaseHistory.getId()));
        return getRepository().findAll(Example.of(toFind));
    }
}
