package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.model.request.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.DiseaseHistoryRepository;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistoryId;
import com.jupiter.asclepi.core.service.api.ClientService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.api.EmployeeService;
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
@Service
@Validated
public class DiseaseHistoryServiceImpl implements DiseaseHistoryService {
    private final DiseaseHistoryRepository repository;
    private final ConversionService conversionService;
    private final ClientService clientService;
    private final EmployeeService employeeService;

    @Override
    public DiseaseHistory create(@Valid @NotNull CreateDiseaseHistoryRequest createRequest) {
        DiseaseHistory toCreate = Objects.requireNonNull(conversionService.convert(createRequest, DiseaseHistory.class));
        Client client = clientService.getOne(createRequest.getClientId())
                .orElseThrow(() -> new NonExistentIdException("Client", createRequest.getClientId()));
        Employee doctor = employeeService.getOne(createRequest.getDoctorId())
                .orElseThrow(() -> new NonExistentIdException("Employee", createRequest.getClientId()));
        toCreate.setDoctor(doctor);
        toCreate.setId(new DiseaseHistoryId(client.getId(), IdGeneratorUtils.generateId().intValue()));
        return repository.save(toCreate);
    }

    @Override
    public Try<DiseaseHistory> edit(@Valid @NotNull EditDiseaseHistoryRequest editRequest) {
        return Try.of(() -> {
            DiseaseHistory existing = getOne(editRequest.getDiseaseHistory())
                    .orElseThrow(() -> new NonExistentIdException("Disease history", editRequest.getDiseaseHistory()));
            if (Objects.nonNull(editRequest.getDoctorId())) {
                employeeService.getOne(editRequest.getDoctorId())
                        .orElseThrow(() -> new NonExistentIdException("Employee", editRequest.getDoctorId()));
            }
            DiseaseHistory toCopyFrom = Objects.requireNonNull(conversionService.convert(editRequest, DiseaseHistory.class));
            CustomBeanUtils.copyPropertiesWithoutNull(toCopyFrom, existing);
            return repository.save(existing);
        });
    }

    @Override
    public List<DiseaseHistory> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<DiseaseHistory> getOne(@Valid @NotNull GetDiseaseHistoryRequest getRequest) {
        DiseaseHistoryId id = Objects.requireNonNull(conversionService.convert(getRequest, DiseaseHistoryId.class));
        return repository.findById(id);
    }

    @Override
    public List<DiseaseHistory> getForClient(Client client) {
        DiseaseHistory toFind = new DiseaseHistory();
        toFind.setClient(client);
        return repository.findAll(Example.of(toFind));
    }
}
