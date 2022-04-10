package com.jupiter.asclepi.core.business;

import com.jupiter.asclepi.core.helper.EmployeeTestHelper;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.model.request.people.EditEmployeeRequest;
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
        helper.assertEntityIsValidAfterCreation(request, created);
    }

    @Test
    void testSuccessfulEdition() throws Exception {
        Employee created = service.create(helper.generateCreateRequest(false)).get();
        entityManager.flush();
        entityManager.detach(created);
        EditEmployeeRequest request = helper.generateEditRequest(created.getId(), true);
        Employee edited = service.edit(request).get();
        helper.assertEntityIsValidAfterEdition(request, edited);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void testSuccessfulGetting() throws Exception {
        Employee created = service.create(helper.generateCreateRequest(false)).get();
        entityManager.flush();
        entityManager.detach(created);
        Employee found = service.getOne(created.getId()).get();
        helper.assertEntitiesAreFullyEqual(created, found);
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
        helper.assertEntitiesAreFullyEqual(one, oneInfo);
        helper.assertEntitiesAreFullyEqual(another, anotherInfo);
    }

    @Test
    void testSuccessfulEmployeeDeletionRequestResponseSignatures() throws Exception {
        Employee created = service.create(helper.generateCreateRequest(false)).get();
        entityManager.flush();
        entityManager.detach(created);
        Assertions.assertTrue(service.delete(created.getId()));
        Assertions.assertFalse(service.getOne(created.getId()).isPresent());
    }
}
