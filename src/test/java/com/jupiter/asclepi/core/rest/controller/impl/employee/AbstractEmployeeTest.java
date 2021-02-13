package com.jupiter.asclepi.core.rest.controller.impl.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.other.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Getter
@RequiredArgsConstructor
public abstract class AbstractEmployeeTest {
    private final ObjectMapper objectMapper;
    private final EntityManager entityManager;

    protected final Map<String, Object> createEmployeeParams() {
        Map<String, Object> createEmployeeParams = new HashMap<>();
        createEmployeeParams.put("login", "testLogin");
        createEmployeeParams.put("password", "testPassword");
        createEmployeeParams.put("role", Role.ADMIN.getId());
        createEmployeeParams.put("name", "testName");
        createEmployeeParams.put("surname", "testSurname");
        return createEmployeeParams;
    }

    protected final Map<String, Object> editEmployeeParams(int employeeId) {
        Map<String, Object> params = createEmployeeParams();
        params.put("id", employeeId);
        return params;
    }

    protected final Employee createTestEmployee() {
        Employee employee = new Employee();
        employee = new Employee();
        employee.setLogin("testLogin");
        employee.setPassword("testPassword");
        employee.setRole(Role.ADMIN);
        employee.setName("testName");
        employee.setSurname("testSurname");
        return employee;
    }

    protected final MockHttpServletRequestBuilder createEmployeeRequest(Map<String, Object> params) throws JsonProcessingException {
        return post("/api/v1/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(params));
    }

    protected final MockHttpServletRequestBuilder editEmployeeRequest(Map<String, Object> params) throws JsonProcessingException {
        return post("/api/v1/employee/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(params));
    }

    protected final MockHttpServletRequestBuilder getEmployeeRequest(int employeeId) {
        return get("/api/v1/employee/{employeeId}", employeeId);
    }

    protected final MockHttpServletRequestBuilder getAllEmployeesRequest() {
        return get("/api/v1/employee/all");
    }

    protected final MockHttpServletRequestBuilder deleteEmployeeRequest(int employeeId) {
        return delete("/api/v1/employee/{employeeId}", employeeId);
    }
}
