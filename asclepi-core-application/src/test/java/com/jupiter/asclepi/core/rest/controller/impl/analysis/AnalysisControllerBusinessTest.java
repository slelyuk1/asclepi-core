package com.jupiter.asclepi.core.rest.controller.impl.analysis;

import com.jupiter.asclepi.core.helper.*;
import com.jupiter.asclepi.core.model.model.entity.analysis.Analysis;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.model.other.Role;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.service.*;
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
public class AnalysisControllerBusinessTest {
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
    private AnalysisTestHelper analysisHelper;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private DiseaseHistoryService diseaseHistoryService;
    @Autowired
    private VisitService visitService;
    @Autowired
    private AnalysisService analysisService;

    private Visit existingVisit;

    @BeforeEach
    void setUp() {
        CreateEmployeeRequest createEmployeeRequest = employeeHelper.generateCreateRequest(true, Role.DOCTOR);
        Employee doctor = employeeService.create(employeeHelper.generateCreateRequest(true, Role.DOCTOR)).get();
        Client client = clientService.create(clientHelper.generateCreateRequest(true)).get();
        DiseaseHistory history = diseaseHistoryService.create(diseaseHistoryHelper.generateCreateRequest(client.getId(), doctor.getId())).get();
        existingVisit = visitService.create(visitHelper.generateCreateRequest(history)).get();
    }


    @Test
    void testSuccessfulCreation() {
        CreateAnalysisRequest request = analysisHelper.generateCreateRequest(existingVisit);
        Analysis created = analysisService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);
        analysisHelper.assertEntityIsValidAfterCreation(request, created);
    }

    @Test
    void testSuccessfulEditing() {
        CreateAnalysisRequest request = analysisHelper.generateCreateRequest(existingVisit);
        Analysis created = analysisService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);

        EditAnalysisRequest editRequest = analysisHelper.generateEditRequest(created);
        Analysis edited = analysisService.edit(editRequest).get();
        analysisHelper.assertEntityIsValidAfterEdition(editRequest, edited);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void testSuccessfulGetting() {
        CreateAnalysisRequest request = analysisHelper.generateCreateRequest(existingVisit);
        Analysis created = analysisService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);

        DiseaseHistory history = created.getVisit().getDiseaseHistory();
        GetAnalysisRequest getRequest = new GetAnalysisRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getNumber()),
                        created.getVisit().getNumber()
                ),
                created.getNumber()
        );
        Analysis found = analysisService.getOne(getRequest).get();
        analysisHelper.assertEntitiesAreFullyEqual(created, found);
    }

    @Test
    void testSuccessfulDeletion() {
        CreateAnalysisRequest request = analysisHelper.generateCreateRequest(existingVisit);
        Analysis created = analysisService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);

        DiseaseHistory history = created.getVisit().getDiseaseHistory();
        GetAnalysisRequest getRequest = new GetAnalysisRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getNumber()),
                        created.getVisit().getNumber()
                ),
                created.getNumber()
        );
        boolean isDeleted = analysisService.delete(getRequest);
        Assertions.assertTrue(isDeleted);
        Assertions.assertFalse(analysisService.getOne(getRequest).isPresent());
    }

    @Test
    void testSuccessfulGettingAll() throws Exception {
        CreateAnalysisRequest request = analysisHelper.generateCreateRequest(existingVisit);
        Analysis one = analysisService.create(request).get();
        Analysis another = analysisService.create(analysisHelper.generateAnotherCreateRequest(request)).get();
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Analysis> all = analysisService.getAll();
        Assertions.assertEquals(all.size(), 2);
        Analysis foundOne = all.stream()
                .filter(analysis -> Objects.equals(analysis.getVisit(), one.getVisit()))
                .filter(analysis -> Objects.equals(analysis.getNumber(), one.getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Analysis foundAnother = all.stream()
                .filter(analysis -> Objects.equals(analysis.getVisit(), another.getVisit()))
                .filter(analysis -> Objects.equals(analysis.getNumber(), another.getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        analysisHelper.assertEntitiesAreFullyEqual(one, foundOne);
        analysisHelper.assertEntitiesAreFullyEqual(another, foundAnother);
    }

    @Test
    void testSuccessfulGettingForVisit() throws Exception {
        CreateAnalysisRequest request = analysisHelper.generateCreateRequest(existingVisit);
        Analysis one = analysisService.create(request).get();
        Analysis another = analysisService.create(analysisHelper.generateAnotherCreateRequest(request)).get();
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Analysis> all = analysisService.getForVisit(one.getVisit());
        Assertions.assertEquals(all.size(), 2);
        Analysis foundOne = all.stream()
                .filter(analysis -> Objects.equals(analysis.getVisit(), one.getVisit()))
                .filter(analysis -> Objects.equals(analysis.getNumber(), one.getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Analysis foundAnother = all.stream()
                .filter(analysis -> Objects.equals(analysis.getVisit(), another.getVisit()))
                .filter(analysis -> Objects.equals(analysis.getNumber(), another.getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        analysisHelper.assertEntitiesAreFullyEqual(one, foundOne);
        analysisHelper.assertEntitiesAreFullyEqual(another, foundAnother);
    }

    @Test
    void testSuccessfulGettingForDiseaseHistory() throws Exception {
        CreateAnalysisRequest request = analysisHelper.generateCreateRequest(existingVisit);
        Analysis one = analysisService.create(request).get();
        Analysis another = analysisService.create(analysisHelper.generateAnotherCreateRequest(request)).get();
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Analysis> all = analysisService.getForDiseaseHistory(one.getVisit().getDiseaseHistory());
        Assertions.assertEquals(all.size(), 2);
        Analysis foundOne = all.stream()
                .filter(analysis -> Objects.equals(analysis.getVisit(), one.getVisit()))
                .filter(analysis -> Objects.equals(analysis.getNumber(), one.getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Analysis foundAnother = all.stream()
                .filter(analysis -> Objects.equals(analysis.getVisit(), another.getVisit()))
                .filter(analysis -> Objects.equals(analysis.getNumber(), another.getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        analysisHelper.assertEntitiesAreFullyEqual(one, foundOne);
        analysisHelper.assertEntitiesAreFullyEqual(another, foundAnother);
    }
}
