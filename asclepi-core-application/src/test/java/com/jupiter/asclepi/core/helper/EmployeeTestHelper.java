package com.jupiter.asclepi.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.request.employee.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.employee.EditEmployeeRequest;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.repository.entity.employee.Role;
import com.jupiter.asclepi.core.repository.helper.api.CreationData;
import com.jupiter.asclepi.core.service.util.SecurityUtils;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.Objects;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class EmployeeTestHelper {
    private static final String TEST_LOGIN = "testLogin";
    private static final String TEST_PASSWORD = "testPassword";
    private static final Role TEST_ROLE = Role.DOCTOR;
    private static final String TEST_NAME = "testName";
    private static final String TEST_SURNAME = "testSurname";
    private static final String TEST_MIDDLE = "testMiddleName";
    private static final String TEST_ADDITIONAL = "testAdditional";

    private final ObjectMapper objectMapper;

    public EmployeeTestHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public CreateEmployeeRequest generateCreateRequest(boolean withOptional) {
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setLogin(TEST_LOGIN);
        request.setPassword(TEST_PASSWORD);
        request.setRoleId(TEST_ROLE.getId());
        request.setName(TEST_NAME);
        request.setSurname(TEST_SURNAME);
        if (withOptional) {
            request.setMiddleName(TEST_MIDDLE);
            request.setAdditionalInfo(TEST_ADDITIONAL);
        }
        return request;
    }

    public CreateEmployeeRequest generateCreateRequest(boolean withOptional, Role role) {
        CreateEmployeeRequest request = generateCreateRequest(withOptional);
        request.setRoleId(role.getId());
        return request;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public CreateEmployeeRequest generateAnotherCreateRequest(CreateEmployeeRequest request) {
        CreateEmployeeRequest another = new CreateEmployeeRequest();
        BeanUtils.copyProperties(request, another);
        another.setLogin(another.getLogin() + "Other");
        another.setName(request.getName() + "Other");
        another.setSurname(another.getSurname() + "Other");
        if (Objects.nonNull(another.getMiddleName())) {
            another.setMiddleName(another.getMiddleName() + "Other");
        }
        if (Objects.nonNull(another.getAdditionalInfo())) {
            another.setAdditionalInfo(another.getAdditionalInfo());
        }
        request.setLogin(request.getLogin() + "Other");
        request.setPassword(request.getPassword() + "Other");
        request.setRoleId(
                Arrays.stream(Role.values())
                        .map(Role::getId)
                        .filter(r -> !request.getRoleId().equals(r))
                        .findAny()
                        .get()
        );
        return another;
    }

    public EditEmployeeRequest generateEditRequest(int id, boolean withOptional) {
        EditEmployeeRequest request = new EditEmployeeRequest();
        request.setId(id);
        request.setLogin(TEST_LOGIN);
        request.setPassword(TEST_PASSWORD);
        request.setRoleId(TEST_ROLE.getId());
        request.setName(TEST_NAME);
        request.setSurname(TEST_SURNAME);
        if (withOptional) {
            request.setMiddleName(TEST_MIDDLE);
        }
        return request;
    }

    public MockHttpServletRequestBuilder createMockedCreateRequest(CreateEmployeeRequest request) throws JsonProcessingException {
        return post("/api/v1/employee/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedEditRequest(EditEmployeeRequest request) throws JsonProcessingException {
        return patch("/api/v1/employee/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetRequest(int id) {
        return get("/api/v1/employee/{employeeId}", id);
    }

    public MockHttpServletRequestBuilder createMockedGetAllRequest() {
        return get("/api/v1/employee/all");
    }

    public MockHttpServletRequestBuilder createMockedDeleteRequest(int id) {
        return delete("/api/v1/employee/{employeeId}", id);
    }

    public void assertEntityIsValidAfterCreation(CreateEmployeeRequest request, Employee entity) {
        Assertions.assertEquals(request.getLogin(), entity.getLogin());
        Assertions.assertEquals(request.getName(), entity.getName());
        Assertions.assertEquals(request.getSurname(), entity.getSurname());
        Assertions.assertEquals(request.getMiddleName(), entity.getMiddleName());
        Assertions.assertEquals(request.getRoleId(), entity.getRole().getId());
        Assertions.assertEquals(request.getAdditionalInfo(), entity.getAdditionalInfo());
        CreationData<String> creation = entity.getCreation();
        Assertions.assertNotNull(creation);
        Assertions.assertNotNull(creation.getWhen());
        String currentLogin = SecurityUtils.getPrincipal(UserDetails.class)
                .map(UserDetails::getUsername)
                .orElse(null);
        Assertions.assertNotNull(currentLogin);
        Assertions.assertEquals(currentLogin, creation.getBy());
    }

    public void assertEntityIsValidAfterEdition(EditEmployeeRequest request, Employee entity) {
        Assertions.assertEquals(request.getId(), entity.getId());
        if (Objects.nonNull(request.getLogin())) {
            Assertions.assertEquals(request.getLogin(), entity.getLogin());
        }
        if (Objects.nonNull(request.getRoleId())) {
            Assertions.assertEquals(request.getRoleId(), entity.getRole().getId());
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

    public void assertEntitiesAreFullyEqual(Employee expected, Employee actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getLogin(), actual.getLogin());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getSurname(), actual.getSurname());
        Assertions.assertEquals(expected.getRole(), actual.getRole());
        Assertions.assertEquals(expected.getMiddleName(), actual.getMiddleName());
        Assertions.assertEquals(expected.getAdditionalInfo(), actual.getAdditionalInfo());
    }
}
