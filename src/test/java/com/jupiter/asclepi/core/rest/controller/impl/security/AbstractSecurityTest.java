package com.jupiter.asclepi.core.rest.controller.impl.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.other.Job;
import com.jupiter.asclepi.core.model.other.Role;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
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
public abstract class AbstractSecurityTest {
    public static final String TEST_LOGIN = "testName";
    public static final String TEST_PASSWORD = "testSurname";

    private final ObjectMapper objectMapper;
    private final EntityManager entityManager;

    protected final Map<String, Object> generateAuthenticateParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("login", TEST_LOGIN);
        params.put("password", TEST_PASSWORD);
        return params;
    }

    protected final CreateEmployeeRequest generateCreateEmployeeRequest(Role role) {
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setLogin(TEST_LOGIN);
        request.setPassword(TEST_PASSWORD);
        request.setRole(role);
        request.setName("testName");
        request.setSurname("testSurname");
        return request;
    }

    protected final MockHttpServletRequestBuilder generateAuthenticationRequest(Map<String, Object> params) throws JsonProcessingException {
        return post("/api/v1/security/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(params));
    }
}
