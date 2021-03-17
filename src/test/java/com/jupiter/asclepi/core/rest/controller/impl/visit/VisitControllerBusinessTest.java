package com.jupiter.asclepi.core.rest.controller.impl.visit;

import com.jupiter.asclepi.core.helper.ClientTestHelper;
import com.jupiter.asclepi.core.helper.DiseaseHistoryTestHelper;
import com.jupiter.asclepi.core.helper.EmployeeTestHelper;
import com.jupiter.asclepi.core.helper.VisitTestHelper;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.other.Role;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.service.ClientService;
import com.jupiter.asclepi.core.service.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.EmployeeService;
import com.jupiter.asclepi.core.service.VisitService;
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
public class VisitControllerBusinessTest {
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
        CreateEmployeeRequest createEmployeeRequest = employeeHelper.generateCreateRequest(true, Role.DOCTOR);
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
        getRequest.setNumber(created.getNumber());
        GetDiseaseHistoryRequest historyGetter = new GetDiseaseHistoryRequest();
        historyGetter.setNumber(created.getDiseaseHistory().getNumber());
        historyGetter.setClientId(created.getDiseaseHistory().getClient().getId());
        getRequest.setDiseaseHistory(historyGetter);
        Visit found = visitService.getOne(getRequest).get();
        visitHelper.assertEntitiesAreFullyEqual(created, found);
    }

    @Test
    void testSuccessfulGettingAll() throws Exception {
        CreateVisitRequest request = visitHelper.generateCreateRequest(existingHistory);
        Visit one = visitService.create(request).get();
        Visit another = visitService.create(visitHelper.generateAnotherCreateRequest(request)).get();
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Visit> all = visitService.getAll();
        Assertions.assertEquals(all.size(), 2);
        Visit foundOne = all.stream()
                .filter(visit -> Objects.equals(visit.getDiseaseHistory(), one.getDiseaseHistory()))
                .filter(visit -> Objects.equals(visit.getNumber(), one.getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Visit foundAnother = all.stream()
                .filter(visit -> Objects.equals(visit.getDiseaseHistory(), another.getDiseaseHistory()))
                .filter(visit -> Objects.equals(visit.getNumber(), another.getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        visitHelper.assertEntitiesAreFullyEqual(one, foundOne);
        visitHelper.assertEntitiesAreFullyEqual(another, foundAnother);
    }
}
