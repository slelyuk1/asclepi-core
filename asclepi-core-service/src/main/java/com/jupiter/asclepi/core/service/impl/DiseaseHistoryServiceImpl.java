package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.model.request.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistoryId;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.service.api.ClientService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.api.EmployeeService;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.service.helper.api.v2.AbstractService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
@Validated
public class DiseaseHistoryServiceImpl extends AbstractService<DiseaseHistory, DiseaseHistoryId> implements DiseaseHistoryService {

    private final ClientService clientService;
    private final EmployeeService employeeService;

    public DiseaseHistoryServiceImpl(ConversionService conversionService,
                                     JpaRepository<DiseaseHistory, DiseaseHistoryId> repository,
                                     ClientService clientService,
                                     EmployeeService employeeService) {
        super(conversionService, repository);
        this.clientService = clientService;
        this.employeeService = employeeService;
    }

    @Override
    public void preProcessBeforeCreation(CreateDiseaseHistoryRequest request, DiseaseHistory toCreate) {
        Client client = clientService.getOne(request.getClientId())
                .orElseThrow(() -> new NonExistentIdException("Client", request.getClientId()));
        Employee doctor = employeeService.getOne(request.getDoctorId())
                .orElseThrow(() -> new NonExistentIdException("Employee", request.getClientId()));
        toCreate.setDoctor(doctor);
        toCreate.setId(new DiseaseHistoryId(client.getId(), (int) getRepository().count()));
    }

    @Override
    public void preProcessBeforeEditing(EditDiseaseHistoryRequest request, DiseaseHistory edited, DiseaseHistory toCopyFrom) {
        if (Objects.nonNull(request.getDoctorId())) {
            Employee doctor = employeeService.getOne(request.getDoctorId())
                    .orElseThrow(() -> new NonExistentIdException("Employee", request.getDoctorId()));
            toCopyFrom.setDoctor(doctor);
        }
    }

    @Override
    public List<DiseaseHistory> getForClient(Client client) {
        DiseaseHistory toFind = new DiseaseHistory();
        toFind.setClient(Client.fromId(client.getId()));
        return getRepository().findAll(Example.of(toFind));
    }

}
