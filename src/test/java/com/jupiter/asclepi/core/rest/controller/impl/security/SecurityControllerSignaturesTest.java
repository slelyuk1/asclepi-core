package com.jupiter.asclepi.core.rest.controller.impl.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.other.Role;
import com.jupiter.asclepi.core.model.request.other.AuthenticationRequest;
import com.jupiter.asclepi.core.service.EmployeeService;
import com.jupiter.asclepi.core.utils.ConstraintDocumentationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class SecurityControllerSignaturesTest extends AbstractSecurityTest {
    private static final FieldDescriptor[] AUTHENTICATE_REQUEST_FIELD_DESCRIPTORS = generateAuthenticateRequestDescriptors();
    private static final FieldDescriptor[] ERROR_INFO_FIELD_DESCRIPTORS = new FieldDescriptor[]{
            fieldWithPath("message").description("Error message").type(JsonFieldType.STRING)
    };

    private MockMvc mockMvc;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    public SecurityControllerSignaturesTest(ObjectMapper objectMapper, EntityManager entityManager) {
        super(objectMapper, entityManager);
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void testSuccessfulAuthenticationRequestSignature() throws Exception {
        employeeService.create(generateCreateEmployeeRequest(Role.ADMIN));
        this.mockMvc.perform(generateAuthenticationRequest(generateAuthenticateParams()))
                .andExpect(status().isOk())
                .andDo(document("securitySuccessfulAuthentication",
                        requestFields(AUTHENTICATE_REQUEST_FIELD_DESCRIPTORS),
                        responseHeaders(
                                headerWithName(HttpHeaders.SET_COOKIE).description("Token which is used for authorization")
                        )
                )).andReturn();
    }

    @Test
    void testForbiddenAuthenticationRequestSignature() throws Exception {
        this.mockMvc.perform(generateAuthenticationRequest(generateAuthenticateParams()))
                .andExpect(status().isForbidden())
                .andDo(document("securityUnsuccessfulAuthentication",
                        requestFields(AUTHENTICATE_REQUEST_FIELD_DESCRIPTORS),
                        responseFields(ERROR_INFO_FIELD_DESCRIPTORS)
                )).andReturn();
    }

    private static FieldDescriptor[] generateAuthenticateRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(AuthenticationRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("login").description("Login of authenticated user").type(JsonFieldType.STRING),
                docHelper.fieldDescriptorFor("password").description("Password of authenticated user").type(JsonFieldType.STRING),
        };
    }
}
