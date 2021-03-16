package com.jupiter.asclepi.core.rest.controller.impl.diseaseHistory;

import com.jupiter.asclepi.core.helper.ClientTestHelper;
import com.jupiter.asclepi.core.helper.DiseaseHistoryTestHelper;
import com.jupiter.asclepi.core.helper.EmployeeTestHelper;
import com.jupiter.asclepi.core.model.entity.disease.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.other.Role;
import com.jupiter.asclepi.core.model.request.disease.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.service.ClientService;
import com.jupiter.asclepi.core.service.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Objects;

@Transactional
@SpringBootTest
public class DiseaseHistoryControllerBusinessTest {

    private final EntityManager entityManager;
    private final EmployeeTestHelper employeeHelper;
    private final ClientTestHelper clientHelper;
    private final DiseaseHistoryTestHelper diseaseHistoryHelper;
    private final EmployeeService employeeService;
    private final ClientService clientService;
    private final DiseaseHistoryService diseaseHistoryService;

    private Employee existingDoctor;
    private Employee anotherDoctor;
    private Client existingClient;

    @Autowired
    public DiseaseHistoryControllerBusinessTest(EntityManager entityManager,
                                                EmployeeTestHelper employeeHelper,
                                                ClientTestHelper clientHelper,
                                                DiseaseHistoryTestHelper diseaseHistoryHelper,
                                                EmployeeService employeeService,
                                                ClientService clientService,
                                                DiseaseHistoryService diseaseHistoryService) {
        this.entityManager = entityManager;
        this.employeeHelper = employeeHelper;
        this.clientHelper = clientHelper;
        this.diseaseHistoryHelper = diseaseHistoryHelper;
        this.employeeService = employeeService;
        this.clientService = clientService;
        this.diseaseHistoryService = diseaseHistoryService;
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
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
        assertEntityIsValidAfterCreation(request, created);
    }

    @Test
    void testSuccessfulEditing() {
        CreateDiseaseHistoryRequest request = new CreateDiseaseHistoryRequest();
        request.setDoctorId(existingDoctor.getId());
        request.setClientId(existingClient.getId());
        DiseaseHistory created = diseaseHistoryService.create(request).get();
        entityManager.flush();
        entityManager.detach(created);

        EditDiseaseHistoryRequest editRequest = diseaseHistoryHelper.generateEditRequest(created, anotherDoctor.getId());
        DiseaseHistory edited = diseaseHistoryService.edit(editRequest).get();
        assertEntityIsValidAfterEdition(editRequest, edited);
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
        assertEntitiesAreFullyEqual(created, found);
    }

    @Test
    void testSuccessfulGettingAll() throws Exception {
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
                .filter(history -> Objects.equals(history.getClient(), one.getClient()))
                .filter(history -> Objects.equals(history.getNumber(), one.getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        DiseaseHistory foundAnother = all.stream()
                .filter(history -> Objects.equals(history.getClient(), another.getClient()))
                .filter(history -> Objects.equals(history.getNumber(), another.getNumber()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        assertEntitiesAreFullyEqual(one, foundOne);
        assertEntitiesAreFullyEqual(another, foundAnother);
    }

    private void assertEntityIsValidAfterCreation(CreateDiseaseHistoryRequest request, DiseaseHistory entity) {
        Assertions.assertEquals(request.getClientId(), entity.getClient().getId());
        Assertions.assertEquals(request.getDoctorId(), entity.getDoctor().getId());
    }

    private void assertEntityIsValidAfterEdition(EditDiseaseHistoryRequest request, DiseaseHistory entity) {
        Assertions.assertEquals(request.getClientId(), entity.getClient().getId());
        Assertions.assertEquals(request.getNumber(), entity.getNumber());
        if (Objects.nonNull(request.getDoctorId())) {
            Assertions.assertEquals(request.getDoctorId(), entity.getDoctor().getId());
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void assertEntitiesAreFullyEqual(DiseaseHistory expected, DiseaseHistory actual) {
        clientHelper.assertEntitiesAreFullyEqual(expected.getClient(), actual.getClient());
        Assertions.assertEquals(expected.getNumber(), actual.getNumber());
        employeeHelper.assertEntitiesAreFullyEqual(expected.getDoctor(), actual.getDoctor());
        Assertions.assertEquals(expected.getDiagnoses(), actual.getDiagnoses());
    }
}
