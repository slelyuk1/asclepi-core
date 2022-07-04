package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.model.request.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.request.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.model.request.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.repository.entity.Anamnesis;
import com.jupiter.asclepi.core.repository.entity.consultation.Consultation;
import com.jupiter.asclepi.core.repository.entity.consultation.ConsultationId;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.service.api.AnamnesisService;
import com.jupiter.asclepi.core.service.api.ConsultationService;
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
import java.util.stream.Collectors;

@Transactional
@Validated
@Service
public class ConsultationServiceImpl extends AbstractService<Consultation, ConsultationId> implements ConsultationService {

    private final VisitService visitService;
    private final AnamnesisService anamnesisService;

    public ConsultationServiceImpl(ConversionService conversionService,
                                   JpaRepository<Consultation, ConsultationId> repository,
                                   VisitService visitService,
                                   AnamnesisService anamnesisService) {
        super(conversionService, repository);
        this.visitService = visitService;
        this.anamnesisService = anamnesisService;
    }

    @Override
    public void preProcessBeforeCreation(CreateConsultationRequest request, Consultation toCreate) {
        Visit visit = visitService.getOne(request.getVisit())
                .orElseThrow(() -> new NonExistentIdException("Visit", request.getVisit()));
        Anamnesis anamnesis = anamnesisService.getOne(request.getAnamnesisId())
                .orElseThrow(() -> new NonExistentIdException("Anamnesis", request.getAnamnesisId()));
        toCreate.setAnamnesis(anamnesis);
        toCreate.setId(new ConsultationId(visit.getId(), (int) getRepository().count()));
    }

    @Override
    public Consultation edit(@Valid @NotNull EditConsultationRequest editRequest) {
        Consultation toCopyFrom = Objects.requireNonNull(getConversionService().convert(editRequest, Consultation.class));
        Consultation toCopyTo = getOne(editRequest.getConsultation())
                .orElseThrow(() -> new NonExistentIdException("Visit", editRequest.getConsultation()));
        if (Objects.nonNull(editRequest.getAnamnesisId())) {
            Anamnesis toSet = anamnesisService.getOne(editRequest.getAnamnesisId())
                    .orElseThrow(() -> new NonExistentIdException("Anamnesis", editRequest.getAnamnesisId()));
            toCopyFrom.setAnamnesis(toSet);
        }
        CustomBeanUtils.copyPropertiesWithoutNull(toCopyFrom, toCopyTo);
        return getRepository().save(toCopyTo);
    }

    @Override
    public List<Consultation> getForVisit(@NotNull Visit visit) {
        Consultation toFind = new Consultation();
        toFind.setVisit(Visit.fromId(visit.getId()));
        return getRepository().findAll(Example.of(toFind));
    }

    @Override
    public List<Consultation> getForDiseaseHistory(@NotNull DiseaseHistory history) {
        return visitService.getForDiseaseHistory(DiseaseHistory.fromId(history.getId())).stream()
                .flatMap(visit -> getForVisit(visit).stream())
                .collect(Collectors.toList());
    }
}
