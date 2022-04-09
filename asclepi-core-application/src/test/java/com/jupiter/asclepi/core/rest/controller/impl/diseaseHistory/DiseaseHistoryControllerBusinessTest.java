package com.jupiter.asclepi.core.rest.controller.impl.diseaseHistory;

import com.jupiter.asclepi.core.helper.ClientTestHelper;
import com.jupiter.asclepi.core.helper.DiseaseHistoryTestHelper;
import com.jupiter.asclepi.core.helper.EmployeeTestHelper;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.model.other.Role;
import com.jupiter.asclepi.core.model.model.request.disease.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.people.CreateEmployeeRequest;
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
public class DiseaseHistoryControllerBusinessTest {

    private final EntityManager entityManager;
    private final DiseaseHistoryTestHelper historyHelper;
    private final EmployeeTestHelper employeeHelper;
    private final ClientTestHelper clientHelper;

    private final EmployeeService employeeService;
    private final ClientService clientService;
    private final DiseaseHistoryService diseaseHistoryService;

    private Employee existingDoctor;
    private Employee anotherDoctor;
    private Client existingClient;

    @Autowired
    public DiseaseHistoryControllerBusinessTest(EntityManager entityManager,
                                                DiseaseHistoryTestHelper historyHelper,
                                                EmployeeTestHelper employeeHelper,
                                                ClientTestHelper clientHelper,
                                                EmployeeService employeeService,
                                                ClientService clientService,
                                                DiseaseHistoryService diseaseHistoryService) {
        this.entityManager = entityManager;
        this.historyHelper = historyHelper;
        this.employeeHelper = employeeHelper;
        this.clientHelper = clientHelper;
        this.employeeService = employeeService;
        this.clientService = clientService;
        this.diseaseHistoryService = diseaseHistoryService;
    }

    @BeforeEach
    void setUp() {
        CreateEmployeeRequest createEmployeeRequest = employeeHelper.generateCreateRequest(true, Role.DOCTOR);
        existingDoctor = employeeService.create(createEmployeeRequest).get();
        CreateEmployeeRequest anotherEmployeeCreateRequest = employeeHelper.generateAnotherCreateRequest(createEmployeeRequest);
        anotherEmployeeCreateRequest.setRole(Role.DOCTOR);
        anotherDoctor = employeeService.create(anotherEmployeeCreateRequest).get();
        existingClient = clientService.create(clientHelper.generateCreateRequest(true)).get();
    }


    @Test
    void testSuccessfulCreation() {
        CreateDiseaseHistoryRequest request = new CreateDiseaseHistoryRequest();
        request.setDoctorId(existingDoctor.getId());
        request.setClientId(existingClient.getId());
        DiseaseHistory created = diseaseHistoryService.create(request).get();

        entityManager.flush();
        entityManager.detach(created);
        historyHelper.assertEntityIsValidAfterCreation(request, created);
    }

    @Test
    void testSuccessfulEditing() {
        CreateDiseaseHistoryRequest request = new CreateDiseaseHistoryRequest();
        request.setDoctorId(existingDoctor.getId());
        request.setClientId(existingClient.getId());
        DiseaseHistory created = diseaseHistoryService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);

        EditDiseaseHistoryRequest editRequest = historyHelper.generateEditRequest(created, anotherDoctor.getId());
        DiseaseHistory edited = diseaseHistoryService.edit(editRequest).get();
        historyHelper.assertEntityIsValidAfterEdition(editRequest, edited);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void testSuccessfulGetting() {
        CreateDiseaseHistoryRequest request = new CreateDiseaseHistoryRequest();
        request.setDoctorId(existingDoctor.getId());
        request.setClientId(existingClient.getId());
        DiseaseHistory created = diseaseHistoryService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);

        GetDiseaseHistoryRequest getRequest = new GetDiseaseHistoryRequest();
        getRequest.setClientId(created.getClient().getId());
        getRequest.setNumber(created.getNumber());
        DiseaseHistory found = diseaseHistoryService.getOne(getRequest).get();
        historyHelper.assertEntitiesAreFullyEqual(created, found);
    }

    @Test
    void testSuccessfulGettingAll() {
        CreateDiseaseHistoryRequest oneRequest = new CreateDiseaseHistoryRequest();
        oneRequest.setDoctorId(existingDoctor.getId());
        oneRequest.setClientId(existingClient.getId());
        DiseaseHistory one = diseaseHistoryService.create(oneRequest).get();
        CreateDiseaseHistoryRequest anotherRequest = oneRequest.clone();
        anotherRequest.setDoctorId(anotherDoctor.getId());
        DiseaseHistory another = diseaseHistoryService.create(anotherRequest).get();
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<DiseaseHistory> all = diseaseHistoryService.getAll();
        Assertions.assertEquals(all.size(), 2);
        DiseaseHistory foundOne = all.stream()
                .filter(history -> Objects.equals(history.getClient().getId(), one.getClient().getId()))
                .filter(history -> Objects.equals(history.getNumber(), one.getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        DiseaseHistory foundAnother = all.stream()
                .filter(history -> Objects.equals(history.getClient().getId(), one.getClient().getId()))
                .filter(history -> Objects.equals(history.getNumber(), another.getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        historyHelper.assertEntitiesAreFullyEqual(one, foundOne);
        historyHelper.assertEntitiesAreFullyEqual(another, foundAnother);
    }

    @Test
    void testSuccessfulGettingForClient() {
        CreateDiseaseHistoryRequest oneRequest = new CreateDiseaseHistoryRequest();
        oneRequest.setDoctorId(existingDoctor.getId());
        oneRequest.setClientId(existingClient.getId());
        DiseaseHistory one = diseaseHistoryService.create(oneRequest).get();
        CreateDiseaseHistoryRequest anotherRequest = oneRequest.clone();
        anotherRequest.setDoctorId(anotherDoctor.getId());
        DiseaseHistory another = diseaseHistoryService.create(anotherRequest).get();
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<DiseaseHistory> all = diseaseHistoryService.getForClient(one.getClient());
        Assertions.assertEquals(all.size(), 2);
        DiseaseHistory foundOne = all.stream()
                .filter(history -> Objects.equals(history.getClient().getId(), one.getClient().getId()))
                .filter(history -> Objects.equals(history.getNumber(), one.getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        DiseaseHistory foundAnother = all.stream()
                .filter(history -> Objects.equals(history.getClient().getId(), one.getClient().getId()))
                .filter(history -> Objects.equals(history.getNumber(), another.getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        historyHelper.assertEntitiesAreFullyEqual(one, foundOne);
        historyHelper.assertEntitiesAreFullyEqual(another, foundAnother);
    }
}
