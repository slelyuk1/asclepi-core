package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.ClientTestHelper;
import com.jupiter.asclepi.core.helper.DiseaseHistoryTestHelper;
import com.jupiter.asclepi.core.helper.EmployeeTestHelper;
import com.jupiter.asclepi.core.helper.VisitTestHelper;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.repository.entity.employee.Role;
import com.jupiter.asclepi.core.service.api.ClientService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.api.EmployeeService;
import com.jupiter.asclepi.core.service.api.VisitService;
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
public class VisitServiceTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EmployeeTestHelper employeeHelper;
    @Autowired
    private ClientTestHelper clientHelper;
    @Autowired
    private DiseaseHistoryTestHelper diseaseHistoryHelper;
    @Autowired
    private VisitTestHelper visitHelper;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private DiseaseHistoryService diseaseHistoryService;
    @Autowired
    private VisitService visitService;

    private DiseaseHistory existingHistory;

    @BeforeEach
    void setUp() {
        Employee doctor = employeeService.create(employeeHelper.generateCreateRequest(true, Role.DOCTOR)).get();
        Client client = clientService.create(clientHelper.generateCreateRequest(true)).get();
        existingHistory = diseaseHistoryService.create(diseaseHistoryHelper.generateCreateRequest(client.getId(), doctor.getId())).get();
    }


    @Test
    void testSuccessfulCreation() {
        CreateVisitRequest request = visitHelper.generateCreateRequest(existingHistory);
        Visit created = visitService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);
        visitHelper.assertEntityIsValidAfterCreation(request, created);
    }

    @Test
    void testSuccessfulEditing() {
        CreateVisitRequest request = visitHelper.generateCreateRequest(existingHistory);
        Visit created = visitService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);

        EditVisitRequest editRequest = visitHelper.generateEditRequest(created, created.getWhen().plusDays(1));
        Visit edited = visitService.edit(editRequest).get();
        visitHelper.assertEntityIsValidAfterEdition(editRequest, edited);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void testSuccessfulGetting() {
        CreateVisitRequest request = visitHelper.generateCreateRequest(existingHistory);
        Visit created = visitService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);

        GetVisitRequest getRequest = new GetVisitRequest();
        getRequest.setNumber(created.getId().getNumber());
        GetDiseaseHistoryRequest historyGetter = new GetDiseaseHistoryRequest();
        historyGetter.setNumber(created.getDiseaseHistory().getId().getNumber());
        historyGetter.setClientId(created.getDiseaseHistory().getClient().getId());
        getRequest.setDiseaseHistory(historyGetter);
        Visit found = visitService.getOne(getRequest).get();
        visitHelper.assertEntitiesAreFullyEqual(created, found);
    }

    @Test
    void testSuccessfulGettingAll() {
        CreateVisitRequest request = visitHelper.generateCreateRequest(existingHistory);
        Visit one = visitService.create(request).get();
        Visit another = visitService.create(visitHelper.generateAnotherCreateRequest(request)).get();
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Visit> all = visitService.getAll();
        Assertions.assertEquals(2, all.size());
        Visit foundOne = all.stream()
                .filter(visit -> Objects.equals(visit.getDiseaseHistory(), one.getDiseaseHistory()))
                .filter(visit -> Objects.equals(visit.getId().getNumber(), one.getId().getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Visit foundAnother = all.stream()
                .filter(visit -> Objects.equals(visit.getDiseaseHistory(), another.getDiseaseHistory()))
                .filter(visit -> Objects.equals(visit.getId().getNumber(), another.getId().getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        visitHelper.assertEntitiesAreFullyEqual(one, foundOne);
        visitHelper.assertEntitiesAreFullyEqual(another, foundAnother);
    }
}
