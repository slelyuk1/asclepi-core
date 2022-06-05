package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.ClientTestHelper;
import com.jupiter.asclepi.core.helper.DiseaseHistoryTestHelper;
import com.jupiter.asclepi.core.helper.EmployeeTestHelper;
import com.jupiter.asclepi.core.model.request.employee.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.repository.entity.employee.Role;
import com.jupiter.asclepi.core.service.api.ClientService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.api.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
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
class DiseaseHistoryServiceTest {

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
    public DiseaseHistoryServiceTest(EntityManager entityManager,
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
        existingDoctor = employeeService.create(createEmployeeRequest);
        CreateEmployeeRequest anotherEmployeeCreateRequest = employeeHelper.generateAnotherCreateRequest(createEmployeeRequest);
        anotherEmployeeCreateRequest.setRoleId(Role.DOCTOR.getId());
        anotherDoctor = employeeService.create(anotherEmployeeCreateRequest);
        existingClient = clientService.create(clientHelper.generateCreateRequest(true));
    }


    @Test
    void testSuccessfulCreation() {
        CreateDiseaseHistoryRequest request = new CreateDiseaseHistoryRequest();
        request.setDoctorId(existingDoctor.getId());
        request.setClientId(existingClient.getId());
        DiseaseHistory created = diseaseHistoryService.create(request);

        entityManager.flush();
        entityManager.detach(created);
        historyHelper.assertEntityIsValidAfterCreation(request, created);
    }

    @Test
    void testSuccessfulEditing() {
        CreateDiseaseHistoryRequest request = new CreateDiseaseHistoryRequest();
        request.setDoctorId(existingDoctor.getId());
        request.setClientId(existingClient.getId());
        DiseaseHistory created = diseaseHistoryService.create(request);
        entityManager.flush();
        entityManager.detach(created);

        EditDiseaseHistoryRequest editRequest = historyHelper.generateEditRequest(created, anotherDoctor.getId());
        DiseaseHistory edited = diseaseHistoryService.edit(editRequest);
        historyHelper.assertEntityIsValidAfterEdition(editRequest, edited);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void testSuccessfulGetting() {
        CreateDiseaseHistoryRequest request = new CreateDiseaseHistoryRequest();
        request.setDoctorId(existingDoctor.getId());
        request.setClientId(existingClient.getId());
        DiseaseHistory created = diseaseHistoryService.create(request);
        entityManager.flush();
        entityManager.detach(created);

        GetDiseaseHistoryRequest getRequest = new GetDiseaseHistoryRequest();
        getRequest.setClientId(created.getClient().getId());
        getRequest.setNumber(created.getId().getNumber());
        DiseaseHistory found = diseaseHistoryService.getOne(getRequest).get();
        historyHelper.assertEntitiesAreFullyEqual(created, found);
    }

    @Test
    void testSuccessfulGettingAll() {
        CreateDiseaseHistoryRequest oneRequest = new CreateDiseaseHistoryRequest();
        oneRequest.setDoctorId(existingDoctor.getId());
        oneRequest.setClientId(existingClient.getId());
        DiseaseHistory one = diseaseHistoryService.create(oneRequest);
        CreateDiseaseHistoryRequest anotherRequest = new CreateDiseaseHistoryRequest();
        BeanUtils.copyProperties(oneRequest, anotherRequest);
        anotherRequest.setDoctorId(anotherDoctor.getId());
        DiseaseHistory another = diseaseHistoryService.create(anotherRequest);
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<DiseaseHistory> all = diseaseHistoryService.getAll();
        Assertions.assertEquals(2, all.size());
        DiseaseHistory foundOne = all.stream()
                .filter(history -> Objects.equals(history.getClient().getId(), one.getClient().getId()))
                .filter(history -> Objects.equals(history.getId().getNumber(), one.getId().getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        DiseaseHistory foundAnother = all.stream()
                .filter(history -> Objects.equals(history.getClient().getId(), one.getClient().getId()))
                .filter(history -> Objects.equals(history.getId().getNumber(), another.getId().getNumber()))
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
        DiseaseHistory one = diseaseHistoryService.create(oneRequest);
        CreateDiseaseHistoryRequest anotherRequest = new CreateDiseaseHistoryRequest();
        BeanUtils.copyProperties(oneRequest, anotherRequest);
        anotherRequest.setDoctorId(anotherDoctor.getId());
        DiseaseHistory another = diseaseHistoryService.create(anotherRequest);
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<DiseaseHistory> all = diseaseHistoryService.getForClient(one.getClient());
        Assertions.assertEquals(2, all.size());
        DiseaseHistory foundOne = all.stream()
                .filter(history -> Objects.equals(history.getClient().getId(), one.getClient().getId()))
                .filter(history -> Objects.equals(history.getId().getNumber(), one.getId().getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        DiseaseHistory foundAnother = all.stream()
                .filter(history -> Objects.equals(history.getClient().getId(), one.getClient().getId()))
                .filter(history -> Objects.equals(history.getId().getNumber(), another.getId().getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        historyHelper.assertEntitiesAreFullyEqual(one, foundOne);
        historyHelper.assertEntitiesAreFullyEqual(another, foundAnother);
    }
}
