package com.jupiter.asclepi.core.rest.controller.impl.history;

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
import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
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

//    @Test
//    void testSuccessfulGettingAll() throws Exception {
//        CreateClientRequest createRequest = helper.generateCreateRequest(false);
//        Client one = service.create(createRequest).get();
//        Client another = service.create(helper.generateAnotherCreateRequest(createRequest)).get();
//        entityManager.flush();
//        entityManager.detach(one);
//        entityManager.detach(another);
//
//        Collection<Client> all = service.getAll();
//        Assertions.assertEquals(all.size(), 2);
//        Client oneInfo = all.stream()
//                .filter(info -> Objects.equals(info.getId(), one.getId()))
//                .findAny()
//                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
//        Client anotherInfo = all.stream()
//                .filter(info -> Objects.equals(info.getId(), another.getId()))
//                .findAny()
//                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
//        assertEntitiesAreFullyEqual(one, oneInfo);
//        assertEntitiesAreFullyEqual(another, anotherInfo);
//    }

    private void assertEntityIsValidAfterCreation(CreateDiseaseHistoryRequest request, DiseaseHistory entity) {
        Assertions.assertEquals(request.getClientId(), entity.getClient().getId());
        Assertions.assertEquals(request.getDoctorId(), entity.getDoctor().getId());
    }

    private void assertEntityIsValidAfterEdition(EditDiseaseHistoryRequest request, DiseaseHistory entity) {
        // todo
//        Assertions.assertEquals(request.getId(), entity.getId());
//        if (Objects.nonNull(request.getName())) {
//            Assertions.assertEquals(request.getName(), entity.getName());
//        }
    }

    @SuppressWarnings("SameParameterValue")
    private void assertEntitiesAreFullyEqual(DiseaseHistory expected, DiseaseHistory actual) {
        // todo
//        Assertions.assertEquals(expected.getClient().getId(), actual.getClient().getId());
//        Assertions.assertEquals(expected.getNumber(), actual.getNumber());
//        Assertions.assertEquals(expected.getDoctor().getId(), actual.getDoctor().getId());
//        Assertions.assertEquals(expected.get, actual.getMiddleName());
//        Assertions.assertEquals(expected.getGender(), actual.getGender());
//        Assertions.assertEquals(expected.getJob(), actual.getJob());
//        Assertions.assertEquals(expected.getResidence(), actual.getResidence());
    }
}
