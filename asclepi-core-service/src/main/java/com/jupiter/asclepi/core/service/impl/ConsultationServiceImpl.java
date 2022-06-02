package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.repository.entity.Anamnesis;
import com.jupiter.asclepi.core.repository.entity.Consultation;
import com.jupiter.asclepi.core.repository.entity.id.ConsultationId;
import com.jupiter.asclepi.core.repository.entity.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.Visit;
import com.jupiter.asclepi.core.model.request.disease.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.repository.ConsultationRepository;
import com.jupiter.asclepi.core.service.api.AnamnesisService;
import com.jupiter.asclepi.core.service.api.ConsultationService;
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
import java.util.stream.Collectors;

@Validated
@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository repository;
    private final VisitService visitService;
    private final AnamnesisService anamnesisService;
    private final ConversionService conversionService;

    @Override
    public Try<Consultation> create(@Valid @NotNull CreateConsultationRequest createRequest) {
        return Try.of(() -> {
            Visit visit = visitService.getOne(createRequest.getVisit())
                    .orElseThrow(() -> new NonExistentIdException("Visit", createRequest.getVisit()));
            Anamnesis anamnesis = anamnesisService.getOne(createRequest.getAnamnesisId())
                    .orElseThrow(() -> new NonExistentIdException("Anamnesis", createRequest.getAnamnesisId()));
            Consultation toCreate = Objects.requireNonNull(conversionService.convert(createRequest, Consultation.class));
            toCreate.setAnamnesis(anamnesis);
            // todo may be better to set in another place
            toCreate.setId(new ConsultationId(visit.getId(), IdGeneratorUtils.generateId().intValue()));
            return repository.save(toCreate);
        });
    }

    @Override
    public Boolean delete(@Valid @NotNull GetConsultationRequest deleteRequest) {
        return getOne(deleteRequest)
                .map(toDelete -> {
                    repository.delete(toDelete);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Try<Consultation> edit(@Valid @NotNull EditConsultationRequest editRequest) {
        return Try.of(() -> {
            Consultation toCopyFrom = Objects.requireNonNull(conversionService.convert(editRequest, Consultation.class));
            Consultation toCopyTo = getOne(editRequest.getConsultation())
                    .orElseThrow(() -> new NonExistentIdException("Visit", editRequest.getConsultation()));
            if (Objects.nonNull(editRequest.getAnamnesisId())) {
                // todo may be better to set in another place
                Anamnesis toSet = anamnesisService.getOne(editRequest.getAnamnesisId())
                        .orElseThrow(() -> new NonExistentIdException("Anamnesis", editRequest.getAnamnesisId()));
                toCopyFrom.setAnamnesis(toSet);
            }
            CustomBeanUtils.copyPropertiesWithoutNull(toCopyFrom, toCopyTo);
            return repository.save(toCopyTo);
        });
    }

    @Override
    public List<Consultation> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Consultation> getOne(@Valid @NotNull GetConsultationRequest getRequest) {
        ConsultationId id = Objects.requireNonNull(conversionService.convert(getRequest, ConsultationId.class));
        return repository.findById(id);
    }

    @Override
    public List<Consultation> getForVisit(@NotNull Visit visit) {
        Consultation toFind = new Consultation();
        toFind.setVisit(visit);
        return repository.findAll(Example.of(toFind));
    }

    @Override
    public List<Consultation> getForDiseaseHistory(@NotNull DiseaseHistory history) {
        return visitService.getForDiseaseHistory(history).stream()
                .flatMap(visit -> getForVisit(visit).stream())
                .collect(Collectors.toList());
    }
}
