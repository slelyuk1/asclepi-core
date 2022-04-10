package com.jupiter.asclepi.core.business;

import com.jupiter.asclepi.core.helper.AnamnesisTestHelper;
import com.jupiter.asclepi.core.helper.ClientTestHelper;
import com.jupiter.asclepi.core.helper.DiseaseHistoryTestHelper;
import com.jupiter.asclepi.core.helper.EmployeeTestHelper;
import com.jupiter.asclepi.core.model.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.model.other.Role;
import com.jupiter.asclepi.core.model.model.request.disease.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.model.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.service.AnamnesisService;
import com.jupiter.asclepi.core.service.ClientService;
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
public class AnamnesisControllerBusinessTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EmployeeTestHelper employeeHelper;
    @Autowired
    private ClientTestHelper clientHelper;
    @Autowired
    private DiseaseHistoryTestHelper diseaseHistoryHelper;
    @Autowired
    private AnamnesisTestHelper anamnesisHelper;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private DiseaseHistoryService diseaseHistoryService;
    @Autowired
    private AnamnesisService anamnesisService;

    private DiseaseHistory existingHistory;

    @BeforeEach
    void setUp() {
        CreateEmployeeRequest createEmployeeRequest = employeeHelper.generateCreateRequest(true, Role.DOCTOR);
        Employee doctor = employeeService.create(employeeHelper.generateCreateRequest(true, Role.DOCTOR)).get();
        Client client = clientService.create(clientHelper.generateCreateRequest(true)).get();
        existingHistory = diseaseHistoryService.create(diseaseHistoryHelper.generateCreateRequest(client.getId(), doctor.getId())).get();
    }


    @Test
    void testSuccessfulCreation() {
        CreateAnamnesisRequest request = anamnesisHelper.generateCreateRequest(existingHistory);
        Anamnesis created = anamnesisService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);
        anamnesisHelper.assertEntityIsValidAfterCreation(request, created);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void testSuccessfulGetting() {
        CreateAnamnesisRequest request = anamnesisHelper.generateCreateRequest(existingHistory);
        Anamnesis created = anamnesisService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);

        Anamnesis found = anamnesisService.getOne(created.getId()).get();
        anamnesisHelper.assertEntitiesAreFullyEqual(created, found);
    }

    @Test
    void testSuccessfulGettingForDiseaseHistory() throws Exception {
        CreateAnamnesisRequest request = anamnesisHelper.generateCreateRequest(existingHistory);
        Anamnesis one = anamnesisService.create(request).get();
        Anamnesis another = anamnesisService.create(anamnesisHelper.generateAnotherCreateRequest(request)).get();
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Anamnesis> all = anamnesisService.getForDiseaseHistory(existingHistory);
        Assertions.assertEquals(all.size(), 2);
        Anamnesis foundOne = all.stream()
                .filter(anamnesis -> Objects.equals(anamnesis, one))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Anamnesis foundAnother = all.stream()
                .filter(anamnesis -> Objects.equals(anamnesis, another))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        anamnesisHelper.assertEntitiesAreFullyEqual(one, foundOne);
        anamnesisHelper.assertEntitiesAreFullyEqual(another, foundAnother);
    }
}
