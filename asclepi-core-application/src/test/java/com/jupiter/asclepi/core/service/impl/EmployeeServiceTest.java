package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.EmployeeTestHelper;
import com.jupiter.asclepi.core.model.request.employee.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.employee.EditEmployeeRequest;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.service.api.EmployeeService;
import org.junit.jupiter.api.Assertions;
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
class EmployeeServiceTest {

    private final EmployeeTestHelper helper;
    private final EmployeeService service;
    private final EntityManager entityManager;

    @Autowired
    public EmployeeServiceTest(EmployeeTestHelper helper, EmployeeService service, EntityManager entityManager) {
        this.helper = helper;
        this.service = service;
        this.entityManager = entityManager;
    }

    @Test
    @WithMockUser
    void testSuccessfulCreation() {
        CreateEmployeeRequest request = helper.generateCreateRequest(false);
        Employee created = service.create(request);
        helper.assertEntityIsValidAfterCreation(request, created);
    }

    @Test
    void testSuccessfulEdition() {
        Employee created = service.create(helper.generateCreateRequest(false));
        entityManager.flush();
        entityManager.detach(created);
        EditEmployeeRequest request = helper.generateEditRequest(created.getId(), true);
        Employee edited = service.edit(request);
        helper.assertEntityIsValidAfterEdition(request, edited);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void testSuccessfulGetting() {
        Employee created = service.create(helper.generateCreateRequest(false));
        entityManager.flush();
        entityManager.detach(created);
        Employee found = service.getOne(created.getId()).get();
        helper.assertEntitiesAreFullyEqual(created, found);
    }

    @Test
    void testSuccessfulAllEmployeesGettingRequestResponseSignatures() {
        CreateEmployeeRequest createRequest = helper.generateCreateRequest(false);
        Employee one = service.create(createRequest);
        Employee another = service.create(helper.generateAnotherCreateRequest(createRequest));
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Employee> all = service.getAll();
        Assertions.assertEquals(2, all.size());
        Employee oneInfo = all.stream()
                .filter(info -> Objects.equals(info.getId(), one.getId()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Employee anotherInfo = all.stream()
                .filter(info -> Objects.equals(info.getId(), another.getId()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        helper.assertEntitiesAreFullyEqual(one, oneInfo);
        helper.assertEntitiesAreFullyEqual(another, anotherInfo);
    }

    @Test
    void testSuccessfulEmployeeDeletionRequestResponseSignatures() {
        Employee created = service.create(helper.generateCreateRequest(false));
        entityManager.flush();
        entityManager.detach(created);
        Assertions.assertTrue(service.delete(created.getId()));
        Assertions.assertFalse(service.getOne(created.getId()).isPresent());
    }
}
