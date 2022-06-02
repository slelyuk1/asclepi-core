package com.jupiter.asclepi.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.repository.entity.Employee;
import com.jupiter.asclepi.core.model.other.Role;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.Objects;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class EmployeeTestHelper {
    private static final String TEST_LOGIN = "testLogin";
    private static final String TEST_PASSWORD = "testPassword";
    private static final Role TEST_ROLE = Role.ADMIN;
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
        request.setRole(TEST_ROLE);
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
        request.setRole(role);
        return request;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public CreateEmployeeRequest generateAnotherCreateRequest(CreateEmployeeRequest request) {
        CreateEmployeeRequest another = request.clone();
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
        request.setRole(
                Arrays.stream(Role.values())
                        .filter(r -> !request.getRole().equals(r))
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
        request.setRole(TEST_ROLE);
        request.setName(TEST_NAME);
        request.setSurname(TEST_SURNAME);
        if (withOptional) {
            request.setMiddleName(TEST_MIDDLE);
        }
        return request;
    }

    public MockHttpServletRequestBuilder createMockedCreateRequest(CreateEmployeeRequest request) throws JsonProcessingException {
        return post("/api/v1/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedEditRequest(EditEmployeeRequest request) throws JsonProcessingException {
        return post("/api/v1/employee/edit")
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
        Assertions.assertEquals(request.getRole(), entity.getRole());
        Assertions.assertEquals(request.getAdditionalInfo(), entity.getAdditionalInfo());
    }

    public void assertEntityIsValidAfterEdition(EditEmployeeRequest request, Employee entity) {
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
