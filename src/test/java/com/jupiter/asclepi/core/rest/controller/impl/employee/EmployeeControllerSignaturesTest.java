package com.jupiter.asclepi.core.rest.controller.impl.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.other.Role;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.utils.ConstraintDocumentationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class EmployeeControllerSignaturesTest {

    private static final Snippet EMPLOYEE_INFO_SNIPPET = responseFields(
            fieldWithPath("id").description("ID of the employee in the system.").type(JsonFieldType.NUMBER),
            fieldWithPath("login").description("Login of the employee in the system.").type(JsonFieldType.STRING),
            fieldWithPath("role").description("Role ID of the employee in the system.").type(JsonFieldType.NUMBER),
            fieldWithPath("name").description("Name of the employee in the system.").type(JsonFieldType.STRING),
            fieldWithPath("surname").description("Surname of the employee in the system.").type(JsonFieldType.STRING),
            fieldWithPath("middleName").description("Middle Name of the employee in the system.").type(JsonFieldType.STRING).optional(),
            fieldWithPath("additionalInfo").description("Additional info about the employee in the system.").type(JsonFieldType.STRING).optional()
    );

    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    private final Map<String, Object> createEmployeeParams;

    EmployeeControllerSignaturesTest() {
        createEmployeeParams = new HashMap<>();
        createEmployeeParams.put("login", "testLogin");
        createEmployeeParams.put("password", "testPassword");
        createEmployeeParams.put("role", Role.ADMIN.getId());
        createEmployeeParams.put("name", "testName");
        createEmployeeParams.put("surname", "testSurname");
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    void testSuccessfulEmployeeCreationRequestResponseSignatures() throws Exception {
        ConstraintDocumentationHelper constraintDocumentationHelper = ConstraintDocumentationHelper.of(CreateEmployeeRequest.class);
        this.mockMvc.perform(createEmployeeRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        requestFields(
                                constraintDocumentationHelper.fieldDescriptorFor("login")
                                        .description("Login of the created employee.").type(JsonFieldType.STRING),
                                constraintDocumentationHelper.fieldDescriptorFor("password")
                                        .description("Password of the created employee.").type(JsonFieldType.STRING),
                                constraintDocumentationHelper.fieldDescriptorFor("role")
                                        .description("Role id of the created employee.").type(JsonFieldType.NUMBER),
                                constraintDocumentationHelper.fieldDescriptorFor("name")
                                        .description("Name of the created employee.").type(JsonFieldType.STRING),
                                constraintDocumentationHelper.fieldDescriptorFor("surname")
                                        .description("Surname of the created employee.").type(JsonFieldType.STRING),
                                constraintDocumentationHelper.fieldDescriptorFor("middleName")
                                        .description("Middle name of the created employee.").type(JsonFieldType.STRING).optional(),
                                constraintDocumentationHelper.fieldDescriptorFor("additionalInfo")
                                        .description("Additional info about the created employee.").type(JsonFieldType.STRING).optional()
                        ),
                        EMPLOYEE_INFO_SNIPPET
                ));
    }

    private MockHttpServletRequestBuilder createEmployeeRequest() throws JsonProcessingException {
        return post("/api/v1/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createEmployeeParams));
    }
}
