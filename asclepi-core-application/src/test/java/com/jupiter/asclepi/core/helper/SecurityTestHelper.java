package com.jupiter.asclepi.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.request.other.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RequiredArgsConstructor
public class SecurityTestHelper {
    public static final String TEST_LOGIN = "testName";
    public static final String TEST_PASSWORD = "testSurname";

    private final ObjectMapper objectMapper;

    public AuthenticationRequest generateAuthenticationRequest() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setLogin(TEST_LOGIN);
        request.setPassword(TEST_PASSWORD);
        return request;
    }

    public MockHttpServletRequestBuilder createMockedAuthenticationRequest(AuthenticationRequest request) throws JsonProcessingException {
        return post("/api/v1/security/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }
}
