package com.jupiter.asclepi.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.other.Role;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.Objects;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@AllArgsConstructor
public class EmployeeTestHelper {
    private static final String TEST_LOGIN = "testLogin";
    private static final String TEST_PASSWORD = "testPassword";
    private static final Role TEST_ROLE = Role.ADMIN;
    private static final String TEST_NAME = "testName";
    private static final String TEST_SURNAME = "testSurname";
    private static final String TEST_MIDDLE = "testMiddleName";
    private static final String TEST_ADDITIONAL = "testAdditional";

    private final ObjectMapper objectMapper;

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
}
