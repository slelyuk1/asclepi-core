package com.jupiter.asclepi.core.rest.controller.impl.diagnosis;

import com.jupiter.asclepi.core.helper.ClientTestHelper;
import com.jupiter.asclepi.core.helper.DiagnosisTestHelper;
import com.jupiter.asclepi.core.helper.DiseaseHistoryTestHelper;
import com.jupiter.asclepi.core.helper.EmployeeTestHelper;
import com.jupiter.asclepi.core.model.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.model.other.Role;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.service.ClientService;
import com.jupiter.asclepi.core.service.DiagnosisService;
import com.jupiter.asclepi.core.service.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Objects;

@Transactional
@SpringBootTest
public class DiagnosisControllerBusinessTest {
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
        Employee doctor = employeeService.create(employeeHelper.generateCreateRequest(true, Role.DOCTOR)).get();
        Client client = clientService.create(clientHelper.generateCreateRequest(true)).get();
        existingHistory = diseaseHistoryService.create(diseaseHistoryHelper.generateCreateRequest(client.getId(), doctor.getId())).get();
    }


    @Test
    void testSuccessfulCreation() {
        CreateDiagnosisRequest request = diagnosisHelper.generateCreateRequest(existingHistory, false);
        Diagnosis created = diagnosisService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);
        diagnosisHelper.assertEntityIsValidAfterCreation(request, created);
    }

    @Test
    void testSuccessfulEditing() {
        CreateDiagnosisRequest request = diagnosisHelper.generateCreateRequest(existingHistory, false);
        Diagnosis created = diagnosisService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);

        EditDiagnosisRequest editRequest = diagnosisHelper.generateEditRequest(created, true);
        Diagnosis edited = diagnosisService.edit(editRequest).get();
        diagnosisHelper.assertEntityIsValidAfterEdition(editRequest, edited);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void testSuccessfulGetting() {
        CreateDiagnosisRequest request = diagnosisHelper.generateCreateRequest(existingHistory, true);
        Diagnosis created = diagnosisService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);

        GetDiagnosisRequest getRequest = new GetDiagnosisRequest(
                new GetDiseaseHistoryRequest(existingHistory.getClient().getId(), existingHistory.getNumber()),
                created.getNumber()
        );
        Diagnosis found = diagnosisService.getOne(getRequest).get();
        diagnosisHelper.assertEntitiesAreFullyEqual(created, found);
    }

    @Test
    void testSuccessfulGettingAll() throws Exception {
        CreateDiagnosisRequest request = diagnosisHelper.generateCreateRequest(existingHistory, false);
        Diagnosis one = diagnosisService.create(request).get();
        Diagnosis another = diagnosisService.create(diagnosisHelper.generateAnotherCreateRequest(request, true)).get();
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Diagnosis> all = diagnosisService.getAll();
        Assertions.assertEquals(all.size(), 2);
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
