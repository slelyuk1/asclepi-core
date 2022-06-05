package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.ClientTestHelper;
import com.jupiter.asclepi.core.helper.DiagnosisTestHelper;
import com.jupiter.asclepi.core.helper.DiseaseHistoryTestHelper;
import com.jupiter.asclepi.core.helper.EmployeeTestHelper;
import com.jupiter.asclepi.core.model.request.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.repository.entity.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.repository.entity.employee.Role;
import com.jupiter.asclepi.core.service.api.ClientService;
import com.jupiter.asclepi.core.service.api.DiagnosisService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.api.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Objects;

@Transactional
@SpringBootTest
@Import(TestHelperConfiguration.class)
class DiagnosisServiceTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EmployeeTestHelper employeeHelper;
    @Autowired
    private ClientTestHelper clientHelper;
    @Autowired
    private DiseaseHistoryTestHelper diseaseHistoryHelper;
    @Autowired
    private DiagnosisTestHelper diagnosisHelper;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private DiseaseHistoryService diseaseHistoryService;
    @Autowired
    private DiagnosisService diagnosisService;

    private DiseaseHistory existingHistory;

    @BeforeEach
    void setUp() {
        Employee doctor = employeeService.create(employeeHelper.generateCreateRequest(true, Role.DOCTOR));
        Client client = clientService.create(clientHelper.generateCreateRequest(true));
        existingHistory = diseaseHistoryService.create(diseaseHistoryHelper.generateCreateRequest(client.getId(), doctor.getId()));
    }


    @Test
    void testSuccessfulCreation() {
        CreateDiagnosisRequest request = diagnosisHelper.generateCreateRequest(existingHistory, false);
        Diagnosis created = diagnosisService.create(request);
        entityManager.flush();
        entityManager.detach(created);
        diagnosisHelper.assertEntityIsValidAfterCreation(request, created);
    }

    @Test
    void testSuccessfulEditing() {
        CreateDiagnosisRequest request = diagnosisHelper.generateCreateRequest(existingHistory, false);
        Diagnosis created = diagnosisService.create(request);
        entityManager.flush();
        entityManager.detach(created);

        EditDiagnosisRequest editRequest = diagnosisHelper.generateEditRequest(created, true);
        Diagnosis edited = diagnosisService.edit(editRequest);
        diagnosisHelper.assertEntityIsValidAfterEdition(editRequest, edited);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void testSuccessfulGetting() {
        CreateDiagnosisRequest request = diagnosisHelper.generateCreateRequest(existingHistory, true);
        Diagnosis created = diagnosisService.create(request);
        entityManager.flush();
        entityManager.detach(created);

        GetDiagnosisRequest getRequest = new GetDiagnosisRequest(
                new GetDiseaseHistoryRequest(existingHistory.getClient().getId(), existingHistory.getId().getNumber()),
                created.getId().getNumber()
        );
        Diagnosis found = diagnosisService.getOne(getRequest).get();
        diagnosisHelper.assertEntitiesAreFullyEqual(created, found);
    }

    @Test
    void testSuccessfulGettingAll() {
        CreateDiagnosisRequest request = diagnosisHelper.generateCreateRequest(existingHistory, false);
        Diagnosis one = diagnosisService.create(request);
        Diagnosis another = diagnosisService.create(diagnosisHelper.generateAnotherCreateRequest(request, true));
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Diagnosis> all = diagnosisService.getAll();
        Assertions.assertEquals(2, all.size());
        Diagnosis foundOne = all.stream()
                .filter(visit -> Objects.equals(visit, one))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Diagnosis foundAnother = all.stream()
                .filter(visit -> Objects.equals(visit, another))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        diagnosisHelper.assertEntitiesAreFullyEqual(one, foundOne);
        diagnosisHelper.assertEntitiesAreFullyEqual(another, foundAnother);
    }
}
