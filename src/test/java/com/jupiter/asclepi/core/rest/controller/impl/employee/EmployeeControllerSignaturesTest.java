package com.jupiter.asclepi.core.rest.controller.impl.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.other.Role;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import com.jupiter.asclepi.core.utils.ConstraintDocumentationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class EmployeeControllerSignaturesTest {

    private static final FieldDescriptor[] EMPLOYEE_INFO_FIELD_DESCRIPTORS = new FieldDescriptor[]{
            fieldWithPath("id").description("ID of the employee in the system").type(JsonFieldType.NUMBER),
            fieldWithPath("login").description("Login of the employee in the system").type(JsonFieldType.STRING),
            fieldWithPath("role").description("Role ID of the employee in the system").type(JsonFieldType.NUMBER),
            fieldWithPath("name").description("Name of the employee in the system").type(JsonFieldType.STRING),
            fieldWithPath("surname").description("Surname of the employee in the system").type(JsonFieldType.STRING),
            fieldWithPath("middleName").description("Middle Name of the employee in the system").type(JsonFieldType.STRING).optional(),
            fieldWithPath("additionalInfo").description("Additional info about the employee in the system").type(JsonFieldType.STRING).optional()
    };

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EntityManager entityManager;
    private MockMvc mockMvc;

    private final Map<String, Object> createEmployeeParams;
    private final Map<String, Object> editEmployeeParams;
    private Employee testEmployee;

    EmployeeControllerSignaturesTest() {
        createEmployeeParams = new HashMap<>();
        createEmployeeParams.put("login", "testLogin");
        createEmployeeParams.put("password", "testPassword");
        createEmployeeParams.put("role", Role.ADMIN.getId());
        createEmployeeParams.put("name", "testName");
        createEmployeeParams.put("surname", "testSurname");

        editEmployeeParams = new HashMap<>(createEmployeeParams);
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
        testEmployee = new Employee();
        testEmployee.setLogin("testLogin");
        testEmployee.setPassword("testPassword");
        testEmployee.setRole(Role.ADMIN);
        testEmployee.setName("testName");
        testEmployee.setSurname("testSurname");
    }

    @Test
    void testSuccessfulEmployeeCreationRequestResponseSignatures() throws Exception {
        ConstraintDocumentationHelper constraintDocumentationHelper = ConstraintDocumentationHelper.of(CreateEmployeeRequest.class);
        this.mockMvc.perform(createEmployeeRequest())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        requestFields(
                                constraintDocumentationHelper.fieldDescriptorFor("login")
                                        .description("Login of the created employee").type(JsonFieldType.STRING),
                                constraintDocumentationHelper.fieldDescriptorFor("password")
                                        .description("Password of the created employee").type(JsonFieldType.STRING),
                                constraintDocumentationHelper.fieldDescriptorFor("role")
                                        .description("Role id of the created employee").type(JsonFieldType.NUMBER),
                                constraintDocumentationHelper.fieldDescriptorFor("name")
                                        .description("Name of the created employee").type(JsonFieldType.STRING),
                                constraintDocumentationHelper.fieldDescriptorFor("surname")
                                        .description("Surname of the created employee").type(JsonFieldType.STRING),
                                constraintDocumentationHelper.fieldDescriptorFor("middleName")
                                        .description("Middle name of the created employee").type(JsonFieldType.STRING).optional(),
                                constraintDocumentationHelper.fieldDescriptorFor("additionalInfo")
                                        .description("Additional info about the created employee").type(JsonFieldType.STRING).optional()
                        ),
                        responseFields(EMPLOYEE_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testSuccessfulEmployeeEditingRequestResponseSignatures() throws Exception {
        entityManager.persist(testEmployee);
        ConstraintDocumentationHelper constraintDocumentationHelper = ConstraintDocumentationHelper.of(EditEmployeeRequest.class);
        this.mockMvc.perform(editEmployeeRequest(testEmployee.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        requestFields(
                                constraintDocumentationHelper.fieldDescriptorFor("id")
                                        .description("ID of the edited employee").type(JsonFieldType.NUMBER),
                                constraintDocumentationHelper.fieldDescriptorFor("login")
                                        .description("Login of the edited employee").type(JsonFieldType.STRING),
                                constraintDocumentationHelper.fieldDescriptorFor("password")
                                        .description("Password of the edited employee").type(JsonFieldType.STRING),
                                constraintDocumentationHelper.fieldDescriptorFor("role")
                                        .description("Role id of the edited employee").type(JsonFieldType.NUMBER),
                                constraintDocumentationHelper.fieldDescriptorFor("name")
                                        .description("Name of the edited employee").type(JsonFieldType.STRING),
                                constraintDocumentationHelper.fieldDescriptorFor("surname")
                                        .description("Surname of the edited employee").type(JsonFieldType.STRING),
                                constraintDocumentationHelper.fieldDescriptorFor("middleName")
                                        .description("Middle name of the edited employee").type(JsonFieldType.STRING).optional(),
                                constraintDocumentationHelper.fieldDescriptorFor("additionalInfo")
                                        .description("Additional info about the edited employee").type(JsonFieldType.STRING).optional()
                        ),
                        responseFields(EMPLOYEE_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testSuccessfulEmployeeGettingRequestResponseSignatures() throws Exception {
        entityManager.persist(testEmployee);
        this.mockMvc.perform(getEmployeeRequest(testEmployee.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        pathParameters(parameterWithName("employeeId").description("ID of the existing employee")),
                        responseFields(EMPLOYEE_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testSuccessfulAllEmployeesGettingRequestResponseSignatures() throws Exception {
        entityManager.persist(testEmployee);
        this.mockMvc.perform(getAllEmployeesRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        responseFields(fieldWithPath("[]").description("Array of EmployeeInfo").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[]", EMPLOYEE_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testSuccessfulEmployeeDeletionRequestResponseSignatures() throws Exception {
        entityManager.persist(testEmployee);
        this.mockMvc.perform(deleteEmployeeRequest(testEmployee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("{method-name}",
                        pathParameters(parameterWithName("employeeId").description("ID of the existing employee"))
                ));
    }

    private MockHttpServletRequestBuilder createEmployeeRequest() throws JsonProcessingException {
        return post("/api/v1/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createEmployeeParams));
    }

    private MockHttpServletRequestBuilder editEmployeeRequest(int employeeId) throws JsonProcessingException {
        editEmployeeParams.put("id", employeeId);
        return post("/api/v1/employee/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editEmployeeParams));
    }

    private MockHttpServletRequestBuilder getEmployeeRequest(int employeeId) {
        return get("/api/v1/employee/{employeeId}", employeeId);
    }

    private MockHttpServletRequestBuilder getAllEmployeesRequest() {
        return get("/api/v1/employee/all");
    }

    private MockHttpServletRequestBuilder deleteEmployeeRequest(int employeeId) {
        return delete("/api/v1/employee/{employeeId}", employeeId);
    }
}
