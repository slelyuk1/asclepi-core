package com.jupiter.asclepi.core.rest.controller.impl.employee;

import com.jupiter.asclepi.core.helper.EmployeeTestHelper;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import com.jupiter.asclepi.core.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Objects;

@Transactional
@SpringBootTest
public class EmployeeControllerBusinessTest {

    private final EmployeeTestHelper helper;
    private final EmployeeService service;
    private final EntityManager entityManager;

    @Autowired
    public EmployeeControllerBusinessTest(EmployeeTestHelper helper, EmployeeService service, EntityManager entityManager) {
        this.helper = helper;
        this.service = service;
        this.entityManager = entityManager;
    }

    @Test
    void testSuccessfulCreation() throws Exception {
        CreateEmployeeRequest request = helper.generateCreateRequest(false);
        Employee created = service.create(request).get();
        assertEntityIsValidAfterCreation(request, created);
    }

    @Test
    void testSuccessfulEdition() throws Exception {
        Employee created = service.create(helper.generateCreateRequest(false)).get();
        entityManager.flush();
        entityManager.detach(created);
        EditEmployeeRequest request = helper.generateEditRequest(created.getId(), true);
        Employee edited = service.edit(request).get();
        assertEntityIsValidAfterEdition(request, edited);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void testSuccessfulGetting() throws Exception {
        Employee created = service.create(helper.generateCreateRequest(false)).get();
        entityManager.flush();
        entityManager.detach(created);
        Employee found = service.getOne(created.getId()).get();
        assertEntitiesAreFullyEqual(created, found);
    }

    @Test
    void testSuccessfulAllEmployeesGettingRequestResponseSignatures() throws Exception {
        CreateEmployeeRequest createRequest = helper.generateCreateRequest(false);
        Employee one = service.create(createRequest).get();
        Employee another = service.create(helper.generateAnotherCreateRequest(createRequest)).get();
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Employee> all = service.getAll();
        Assertions.assertEquals(all.size(), 3);
        Employee oneInfo = all.stream()
                .filter(info -> Objects.equals(info.getId(), one.getId()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Employee anotherInfo = all.stream()
                .filter(info -> Objects.equals(info.getId(), another.getId()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        assertEntitiesAreFullyEqual(one, oneInfo);
        assertEntitiesAreFullyEqual(another, anotherInfo);
    }

    @Test
    void testSuccessfulEmployeeDeletionRequestResponseSignatures() throws Exception {
        Employee created = service.create(helper.generateCreateRequest(false)).get();
        entityManager.flush();
        entityManager.detach(created);
        Assertions.assertTrue(service.delete(created.getId()));
        Assertions.assertFalse(service.getOne(created.getId()).isPresent());
    }

    private void assertEntityIsValidAfterCreation(CreateEmployeeRequest request, Employee entity) {
        Assertions.assertEquals(request.getLogin(), entity.getLogin());
        Assertions.assertEquals(request.getName(), entity.getName());
        Assertions.assertEquals(request.getSurname(), entity.getSurname());
        Assertions.assertEquals(request.getMiddleName(), entity.getMiddleName());
        Assertions.assertEquals(request.getRole(), entity.getRole());
        Assertions.assertEquals(request.getAdditionalInfo(), entity.getAdditionalInfo());
    }

    private void assertEntityIsValidAfterEdition(EditEmployeeRequest request, Employee entity) {
        Assertions.assertEquals(request.getId(), entity.getId());
        if (Objects.nonNull(request.getLogin())) {
            Assertions.assertEquals(request.getLogin(), entity.getLogin());
        }
        if (Objects.nonNull(request.getRole())) {
            Assertions.assertEquals(request.getRole(), entity.getRole());
        }
        if (Objects.nonNull(request.getName())) {
            Assertions.assertEquals(request.getName(), entity.getName());
        }
        if (Objects.nonNull(request.getSurname())) {
            Assertions.assertEquals(request.getSurname(), entity.getSurname());
        }
        if (Objects.nonNull(request.getMiddleName())) {
            Assertions.assertEquals(request.getMiddleName(), entity.getMiddleName());
        }
        if (Objects.nonNull(request.getAdditionalInfo())) {
            Assertions.assertEquals(request.getAdditionalInfo(), entity.getAdditionalInfo());
        }
    }

    private void assertEntitiesAreFullyEqual(Employee expected, Employee actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getLogin(), actual.getLogin());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getSurname(), actual.getSurname());
        Assertions.assertEquals(expected.getRole(), actual.getRole());
        Assertions.assertEquals(expected.getMiddleName(), actual.getMiddleName());
        Assertions.assertEquals(expected.getAdditionalInfo(), actual.getAdditionalInfo());
    }
}
