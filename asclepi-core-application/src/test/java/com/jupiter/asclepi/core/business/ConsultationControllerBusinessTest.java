package com.jupiter.asclepi.core.business;

import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.*;
import com.jupiter.asclepi.core.model.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.model.entity.disease.consultation.Consultation;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.model.other.Role;
import com.jupiter.asclepi.core.model.model.request.disease.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.model.request.disease.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.model.model.request.disease.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.service.api.*;
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
class ConsultationControllerBusinessTest {
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
    private AnamnesisTestHelper anamnesisHelper;
    @Autowired
    private ConsultationTestHelper consultationHelper;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private DiseaseHistoryService diseaseHistoryService;
    @Autowired
    private VisitService visitService;
    @Autowired
    private AnamnesisService anamnesisService;
    @Autowired
    private ConsultationService consultationService;

    private Visit existingVisit;
    private Anamnesis existingAnamnesis;

    @BeforeEach
    void setUp() {
        Employee doctor = employeeService.create(employeeHelper.generateCreateRequest(true, Role.DOCTOR)).get();
        Client client = clientService.create(clientHelper.generateCreateRequest(true)).get();
        DiseaseHistory history = diseaseHistoryService.create(diseaseHistoryHelper.generateCreateRequest(client.getId(), doctor.getId())).get();
        existingVisit = visitService.create(visitHelper.generateCreateRequest(history)).get();
        existingAnamnesis = anamnesisService.create(anamnesisHelper.generateCreateRequest(history)).get();
    }


    @Test
    void testSuccessfulCreation() {
        CreateConsultationRequest request = consultationHelper.generateCreateRequest(existingVisit, existingAnamnesis);
        Consultation created = consultationService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);
        consultationHelper.assertEntityIsValidAfterCreation(request, created);
    }

    @Test
    void testSuccessfulEditing() {
        CreateConsultationRequest request = consultationHelper.generateCreateRequest(existingVisit, existingAnamnesis);
        Consultation created = consultationService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);

        EditConsultationRequest editRequest = consultationHelper.generateEditRequest(created);
        Consultation edited = consultationService.edit(editRequest).get();
        consultationHelper.assertEntityIsValidAfterEdition(editRequest, edited);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void testSuccessfulGetting() {
        CreateConsultationRequest request = consultationHelper.generateCreateRequest(existingVisit, existingAnamnesis);
        Consultation created = consultationService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);

        DiseaseHistory history = created.getVisit().getDiseaseHistory();
        GetConsultationRequest getRequest = new GetConsultationRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getNumber()),
                        created.getVisit().getNumber()
                ),
                created.getNumber()
        );
        Consultation found = consultationService.getOne(getRequest).get();
        consultationHelper.assertEntitiesAreFullyEqual(created, found);
    }

    @Test
    void testSuccessfulDeletion() {
        CreateConsultationRequest request = consultationHelper.generateCreateRequest(existingVisit, existingAnamnesis);
        Consultation created = consultationService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);

        DiseaseHistory history = created.getVisit().getDiseaseHistory();
        GetConsultationRequest getRequest = new GetConsultationRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getNumber()),
                        created.getVisit().getNumber()
                ),
                created.getNumber()
        );
        boolean isDeleted = consultationService.delete(getRequest);
        Assertions.assertTrue(isDeleted);
        Assertions.assertFalse(consultationService.getOne(getRequest).isPresent());
    }

    @Test
    void testSuccessfulGettingAll() {
        CreateConsultationRequest request = consultationHelper.generateCreateRequest(existingVisit, existingAnamnesis);
        Consultation one = consultationService.create(request).get();
        Consultation another = consultationService.create(consultationHelper.generateAnotherCreateRequest(request)).get();
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Consultation> all = consultationService.getAll();
        Assertions.assertEquals(2, all.size());
        Consultation foundOne = all.stream()
                .filter(consultation -> Objects.equals(consultation, one))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Consultation foundAnother = all.stream()
                .filter(consultation -> Objects.equals(consultation, another))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        consultationHelper.assertEntitiesAreFullyEqual(one, foundOne);
        consultationHelper.assertEntitiesAreFullyEqual(another, foundAnother);
    }

    @Test
    void testSuccessfulGettingForVisit() {
        CreateConsultationRequest request = consultationHelper.generateCreateRequest(existingVisit, existingAnamnesis);
        Consultation one = consultationService.create(request).get();
        Consultation another = consultationService.create(consultationHelper.generateAnotherCreateRequest(request)).get();
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Consultation> all = consultationService.getForVisit(one.getVisit());
        Assertions.assertEquals(2, all.size());
        Consultation foundOne = all.stream()
                .filter(consultation -> Objects.equals(consultation, one))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Consultation foundAnother = all.stream()
                .filter(consultation -> Objects.equals(consultation, another))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        consultationHelper.assertEntitiesAreFullyEqual(one, foundOne);
        consultationHelper.assertEntitiesAreFullyEqual(another, foundAnother);
    }

    @Test
    void testSuccessfulGettingForDiseaseHistory() {
        CreateConsultationRequest request = consultationHelper.generateCreateRequest(existingVisit, existingAnamnesis);
        Consultation one = consultationService.create(request).get();
        Consultation another = consultationService.create(consultationHelper.generateAnotherCreateRequest(request)).get();
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Consultation> all = consultationService.getForDiseaseHistory(one.getVisit().getDiseaseHistory());
        Assertions.assertEquals(2, all.size());
        Consultation foundOne = all.stream()
                .filter(consultation -> Objects.equals(consultation, one))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Consultation foundAnother = all.stream()
                .filter(consultation -> Objects.equals(consultation, another))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        consultationHelper.assertEntitiesAreFullyEqual(one, foundOne);
        consultationHelper.assertEntitiesAreFullyEqual(another, foundAnother);
    }
}
