package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.*;
import com.jupiter.asclepi.core.model.request.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.request.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.request.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.repository.entity.analysis.Analysis;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.repository.entity.employee.Role;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.service.api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Objects;

@Transactional
@SpringBootTest
@Import(TestHelperConfiguration.class)
class AnalysisServiceTest {
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
        Employee doctor = employeeService.create(employeeHelper.generateCreateRequest(true, Role.DOCTOR));
        Client client = clientService.create(clientHelper.generateCreateRequest(true));
        DiseaseHistory history = diseaseHistoryService.create(diseaseHistoryHelper.generateCreateRequest(client.getId(), doctor.getId()));
        existingVisit = visitService.create(visitHelper.generateCreateRequest(history));
    }


    @Test
    @WithMockUser
    void testSuccessfulCreation() {
        CreateAnalysisRequest request = analysisHelper.generateCreateRequest(existingVisit);
        Analysis created = analysisService.create(request);
        entityManager.flush();
        entityManager.detach(created);
        analysisHelper.assertEntityIsValidAfterCreation(request, created);
    }

    @Test
    void testSuccessfulEditing() {
        CreateAnalysisRequest request = analysisHelper.generateCreateRequest(existingVisit);
        Analysis created = analysisService.create(request);
        entityManager.flush();
        entityManager.detach(created);

        EditAnalysisRequest editRequest = analysisHelper.generateEditRequest(created);
        Analysis edited = analysisService.edit(editRequest);
        analysisHelper.assertEntityIsValidAfterEdition(editRequest, edited);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void testSuccessfulGetting() {
        CreateAnalysisRequest request = analysisHelper.generateCreateRequest(existingVisit);
        Analysis created = analysisService.create(request);
        entityManager.flush();
        entityManager.detach(created);

        DiseaseHistory history = created.getVisit().getDiseaseHistory();
        GetAnalysisRequest getRequest = new GetAnalysisRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getId().getNumber()),
                        created.getVisit().getId().getNumber()
                ),
                created.getId().getNumber()
        );
        Analysis found = analysisService.getOne(getRequest).get();
        analysisHelper.assertEntitiesAreFullyEqual(created, found);
    }

    @Test
    void testSuccessfulDeletion() {
        CreateAnalysisRequest request = analysisHelper.generateCreateRequest(existingVisit);
        Analysis created = analysisService.create(request);
        entityManager.flush();
        entityManager.detach(created);

        DiseaseHistory history = created.getVisit().getDiseaseHistory();
        GetAnalysisRequest getRequest = new GetAnalysisRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getId().getNumber()),
                        created.getVisit().getId().getNumber()
                ),
                created.getId().getNumber()
        );
        boolean isDeleted = analysisService.delete(getRequest);
        Assertions.assertTrue(isDeleted);
        Assertions.assertFalse(analysisService.getOne(getRequest).isPresent());
    }

    @Test
    void testSuccessfulGettingAll() {
        CreateAnalysisRequest request = analysisHelper.generateCreateRequest(existingVisit);
        Analysis one = analysisService.create(request);
        Analysis another = analysisService.create(analysisHelper.generateAnotherCreateRequest(request));
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Analysis> all = analysisService.getAll();
        Assertions.assertEquals(2, all.size());
        Analysis foundOne = all.stream()
                .filter(analysis -> Objects.equals(analysis.getVisit(), one.getVisit()))
                .filter(analysis -> Objects.equals(analysis.getId().getNumber(), one.getId().getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Analysis foundAnother = all.stream()
                .filter(analysis -> Objects.equals(analysis.getVisit(), another.getVisit()))
                .filter(analysis -> Objects.equals(analysis.getId().getNumber(), another.getId().getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        analysisHelper.assertEntitiesAreFullyEqual(one, foundOne);
        analysisHelper.assertEntitiesAreFullyEqual(another, foundAnother);
    }

    @Test
    void testSuccessfulGettingForVisit() {
        CreateAnalysisRequest request = analysisHelper.generateCreateRequest(existingVisit);
        Analysis one = analysisService.create(request);
        Analysis another = analysisService.create(analysisHelper.generateAnotherCreateRequest(request));
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Analysis> all = analysisService.getForVisit(one.getVisit());
        Assertions.assertEquals(2, all.size());
        Analysis foundOne = all.stream()
                .filter(analysis -> Objects.equals(analysis.getVisit(), one.getVisit()))
                .filter(analysis -> Objects.equals(analysis.getId().getNumber(), one.getId().getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Analysis foundAnother = all.stream()
                .filter(analysis -> Objects.equals(analysis.getVisit(), another.getVisit()))
                .filter(analysis -> Objects.equals(analysis.getId().getNumber(), another.getId().getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        analysisHelper.assertEntitiesAreFullyEqual(one, foundOne);
        analysisHelper.assertEntitiesAreFullyEqual(another, foundAnother);
    }

    @Test
    void testSuccessfulGettingForDiseaseHistory() {
        CreateAnalysisRequest request = analysisHelper.generateCreateRequest(existingVisit);
        Analysis one = analysisService.create(request);
        Analysis another = analysisService.create(analysisHelper.generateAnotherCreateRequest(request));
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Analysis> all = analysisService.getForDiseaseHistory(one.getVisit().getDiseaseHistory());
        Assertions.assertEquals(2, all.size());
        Analysis foundOne = all.stream()
                .filter(analysis -> Objects.equals(analysis.getVisit(), one.getVisit()))
                .filter(analysis -> Objects.equals(analysis.getId().getNumber(), one.getId().getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Analysis foundAnother = all.stream()
                .filter(analysis -> Objects.equals(analysis.getVisit(), another.getVisit()))
                .filter(analysis -> Objects.equals(analysis.getId().getNumber(), another.getId().getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        analysisHelper.assertEntitiesAreFullyEqual(one, foundOne);
        analysisHelper.assertEntitiesAreFullyEqual(another, foundAnother);
    }
}
