package com.jupiter.asclepi.core.rest.controller.impl.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
public class EmployeeControllerServiceTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EntityManager entityManager;
    private MockMvc mockMvc;

    private final Map<String, Object> createEmployeeParams;

    EmployeeControllerServiceTest() {
        createEmployeeParams = new HashMap<>();
        createEmployeeParams.put("login", "testLogin");
        createEmployeeParams.put("password", "testPassword");
        createEmployeeParams.put("role", Role.ADMIN.getId());
        createEmployeeParams.put("name", "testName");
        createEmployeeParams.put("surname", "testSurname");
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testSuccessfulEmployeeCreation() throws Exception {
        this.mockMvc.perform(createEmployeeRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    EmployeeInfo employeeInfo = objectMapper.readValue(result.getResponse().getContentAsString(), EmployeeInfo.class);
                    Employee createdEmployee = entityManager.find(Employee.class, employeeInfo.getId());
                    Assertions.assertEquals(employeeInfo.getLogin(), createdEmployee.getLogin());
                    Assertions.assertEquals(employeeInfo.getName(), createdEmployee.getName());
                    Assertions.assertEquals(employeeInfo.getSurname(), createdEmployee.getSurname());
                    Assertions.assertEquals(employeeInfo.getRole(), createdEmployee.getRole());
                    Assertions.assertEquals(employeeInfo.getMiddleName(), createdEmployee.getMiddleName());
                    Assertions.assertEquals(employeeInfo.getAdditionalInfo(), createdEmployee.getAdditionalInfo());
                });
    }

    private MockHttpServletRequestBuilder createEmployeeRequest() throws JsonProcessingException {
        return post("/api/v1/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createEmployeeParams));
    }
}
