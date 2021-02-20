package com.jupiter.asclepi.core.rest.controller.impl.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.other.Role;
import com.jupiter.asclepi.core.model.response.people.EmployeeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
// todo refactor (Oleksandr Leliuk)
public class EmployeeControllerBusinessTest extends AbstractEmployeeTest {

    private MockMvc mockMvc;

    @Autowired
    public EmployeeControllerBusinessTest(ObjectMapper objectMapper, EntityManager entityManager) {
        super(objectMapper, entityManager);
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testSuccessfulEmployeeCreation() throws Exception {
        this.mockMvc.perform(createEmployeeRequest(createEmployeeParams()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    EmployeeInfo employeeInfo = getObjectMapper().readValue(result.getResponse().getContentAsString(), EmployeeInfo.class);
                    Employee createdEmployee = getEntityManager().find(Employee.class, employeeInfo.getId());
                    assertEmployeeIsEqualToEmployeeInfo(createdEmployee, employeeInfo);
                });
    }

    @Test
    void testSuccessfulEmployeeEditing() throws Exception {
        Employee toEdit = createAnotherEmployee();
        getEntityManager().persist(toEdit);

        this.mockMvc.perform(editEmployeeRequest(editEmployeeParams(toEdit.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    EmployeeInfo employeeInfo = getObjectMapper().readValue(result.getResponse().getContentAsString(), EmployeeInfo.class);
                    assertEmployeeIsEqualToEmployeeInfo(toEdit, employeeInfo);
                });
    }

    @Test
    void testSuccessfulEmployeeGettingRequestResponseSignatures() throws Exception {
        Employee testEmployee = createTestEmployee();
        getEntityManager().persist(testEmployee);
        this.mockMvc.perform(getEmployeeRequest(testEmployee.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    EmployeeInfo employeeInfo = getObjectMapper().readValue(result.getResponse().getContentAsString(), EmployeeInfo.class);
                    assertEmployeeIsEqualToEmployeeInfo(testEmployee, employeeInfo);
                });
    }

    @Test
    void testSuccessfulAllEmployeesGettingRequestResponseSignatures() throws Exception {
        Employee testEmployee = createTestEmployee();
        Employee anotherEmployee = createAnotherEmployee();
        getEntityManager().persist(testEmployee);
        getEntityManager().persist(anotherEmployee);
        this.mockMvc.perform(getAllEmployeesRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    EmployeeInfo[] employeeInfos = getObjectMapper().readValue(result.getResponse().getContentAsString(), EmployeeInfo[].class);
                    Assertions.assertEquals(employeeInfos.length, 2);
                    EmployeeInfo testEmployeeInfo = Arrays.stream(employeeInfos)
                            .filter(employeeInfo -> employeeInfo.getId() == testEmployee.getId())
                            .findAny()
                            .orElseThrow(() -> new IllegalStateException("EmployeeInfo list doesn't contain existing employee!"));
                    EmployeeInfo anotherEmployeeInfo = Arrays.stream(employeeInfos)
                            .filter(employeeInfo -> employeeInfo.getId() == anotherEmployee.getId())
                            .findAny()
                            .orElseThrow(() -> new IllegalStateException("EmployeeInfo list doesn't contain existing employee!"));
                    assertEmployeeIsEqualToEmployeeInfo(testEmployee, testEmployeeInfo);
                    assertEmployeeIsEqualToEmployeeInfo(anotherEmployee, anotherEmployeeInfo);
                });
    }

    @Test
    void testSuccessfulEmployeeDeletionRequestResponseSignatures() throws Exception {
        Employee testEmployee = createTestEmployee();
        getEntityManager().persist(testEmployee);
        this.mockMvc.perform(deleteEmployeeRequest(testEmployee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(result -> {
                    Employee found = getEntityManager().find(Employee.class, testEmployee.getId());
                    Assertions.assertNull(found);
                });
    }

    private Employee createAnotherEmployee() {
        Employee otherEmployee = new Employee();
        otherEmployee.setLogin("oldLogin");
        otherEmployee.setPassword("password");
        otherEmployee.setRole(Role.DOCTOR);
        otherEmployee.setName("oldName");
        otherEmployee.setSurname("oldSurname");
        return otherEmployee;
    }

    private void assertEmployeeIsEqualToEmployeeInfo(Employee employee, EmployeeInfo employeeInfo) {
        Assertions.assertEquals(employee.getId(), employeeInfo.getId());
        Assertions.assertEquals(employee.getLogin(), employeeInfo.getLogin());
        Assertions.assertEquals(employee.getName(), employeeInfo.getName());
        Assertions.assertEquals(employee.getSurname(), employeeInfo.getSurname());
        Assertions.assertEquals(employee.getRole(), employeeInfo.getRole());
        Assertions.assertEquals(employee.getMiddleName(), employeeInfo.getMiddleName());
        Assertions.assertEquals(employee.getAdditionalInfo(), employeeInfo.getAdditionalInfo());
    }
}
