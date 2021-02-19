package com.jupiter.asclepi.core.rest.controller.impl.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.entity.people.Employee;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class EmployeeControllerSignaturesTest extends AbstractEmployeeTest {

    private static final FieldDescriptor[] EMPLOYEE_INFO_FIELD_DESCRIPTORS = new FieldDescriptor[]{
            fieldWithPath("id").description("ID of the employee in the system").type(JsonFieldType.NUMBER),
            fieldWithPath("login").description("Login of the employee in the system").type(JsonFieldType.STRING),
            fieldWithPath("role").description("Role ID of the employee in the system").type(JsonFieldType.NUMBER),
            fieldWithPath("name").description("Name of the employee in the system").type(JsonFieldType.STRING),
            fieldWithPath("surname").description("Surname of the employee in the system").type(JsonFieldType.STRING),
            fieldWithPath("middleName").description("Middle Name of the employee in the system").type(JsonFieldType.STRING).optional(),
            fieldWithPath("additionalInfo").description("Additional info about the employee in the system").type(JsonFieldType.STRING).optional()
    };

    private static final FieldDescriptor[] CREATE_EMPLOYEE_REQUEST_FIELD_DESCRIPTORS = generateCreateEmployeeRequestDescriptors();
    private static final FieldDescriptor[] EDIT_EMPLOYEE_REQUEST_FIELD_DESCRIPTORS = generateEditEmployeeRequestDescriptors();
    private static final FieldDescriptor[] ERROR_INFO_FIELD_DESCRIPTORS = new FieldDescriptor[]{
            fieldWithPath("message").description("Error message").type(JsonFieldType.STRING)
    };

    private MockMvc mockMvc;

    @Autowired
    public EmployeeControllerSignaturesTest(ObjectMapper objectMapper, EntityManager entityManager) {
        super(objectMapper, entityManager);
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
        this.mockMvc.perform(createEmployeeRequest(createEmployeeParams()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        requestFields(CREATE_EMPLOYEE_REQUEST_FIELD_DESCRIPTORS),
                        responseFields(EMPLOYEE_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testFailedDueToExistingLoginEmployeeCreationRequestResponseSignature() throws Exception {
        Employee testEmployee = createTestEmployee();
        getEntityManager().persist(testEmployee);
        this.mockMvc.perform(createEmployeeRequest(createEmployeeParams()))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        requestFields(CREATE_EMPLOYEE_REQUEST_FIELD_DESCRIPTORS),
                        responseFields(ERROR_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testSuccessfulEmployeeEditingRequestResponseSignatures() throws Exception {
        Employee testEmployee = createTestEmployee();
        getEntityManager().persist(testEmployee);
        this.mockMvc.perform(editEmployeeRequest(editEmployeeParams(testEmployee.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        requestFields(EDIT_EMPLOYEE_REQUEST_FIELD_DESCRIPTORS),
                        responseFields(EMPLOYEE_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testFailedDueToNonExistentEmployeeEditingRequestResponseSignatures() throws Exception {
        Employee testEmployee = createTestEmployee();
        getEntityManager().persist(testEmployee);
        this.mockMvc.perform(editEmployeeRequest(editEmployeeParams(0)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("{method-name}",
                        requestFields(EDIT_EMPLOYEE_REQUEST_FIELD_DESCRIPTORS)
                ));
    }

    // todo
//    @Test
//    void testFailedDueToExistingLoginEmployeeEditingRequestResponseSignatures() throws Exception {
//        Employee testEmployee = createTestEmployee();
//        getEntityManager().persist(testEmployee);
//        this.mockMvc.perform(editEmployeeRequest(editEmployeeParams(testEmployee.getId())))
//                .andExpect(status().isConflict())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andDo(document("{method-name}",
//                        requestFields(EDIT_EMPLOYEE_REQUEST_FIELD_DESCRIPTORS),
//                        responseFields(ERROR_INFO_FIELD_DESCRIPTORS)
//                ));
//    }

    @Test
    void testSuccessfulEmployeeGettingRequestResponseSignatures() throws Exception {
        Employee testEmployee = createTestEmployee();
        getEntityManager().persist(testEmployee);
        this.mockMvc.perform(getEmployeeRequest(testEmployee.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        pathParameters(parameterWithName("employeeId").description("ID of the existing employee")),
                        responseFields(EMPLOYEE_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testNonExistentEmployeeGettingRequestResponseSignatures() throws Exception {
        this.mockMvc.perform(getEmployeeRequest(0))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("{method-name}",
                        pathParameters(parameterWithName("employeeId").description("ID of the existing employee"))
                ));
    }

    @Test
    void testSuccessfulAllEmployeesGettingRequestResponseSignatures() throws Exception {
        Employee testEmployee = createTestEmployee();
        getEntityManager().persist(testEmployee);
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
        Employee testEmployee = createTestEmployee();
        getEntityManager().persist(testEmployee);
        this.mockMvc.perform(deleteEmployeeRequest(testEmployee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("{method-name}",
                        pathParameters(parameterWithName("employeeId").description("ID of the existing employee"))
                ));
    }

    @Test
    void testNonExistentEmployeeDeletionRequestResponseSignatures() throws Exception {
        Employee testEmployee = createTestEmployee();
        getEntityManager().persist(testEmployee);
        this.mockMvc.perform(deleteEmployeeRequest(0))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("{method-name}",
                        pathParameters(parameterWithName("employeeId").description("ID of the existing employee"))
                ));
    }

    private static FieldDescriptor[] generateCreateEmployeeRequestDescriptors() {
        ConstraintDocumentationHelper constraintDocumentationHelper = ConstraintDocumentationHelper.of(CreateEmployeeRequest.class);
        return new FieldDescriptor[]{
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
        };
    }

    private static FieldDescriptor[] generateEditEmployeeRequestDescriptors() {
        ConstraintDocumentationHelper constraintDocumentationHelper = ConstraintDocumentationHelper.of(EditEmployeeRequest.class);
        return new FieldDescriptor[]{
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
        };
    }
}
