package com.jupiter.asclepi.core.rest;

import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.EmployeeTestHelper;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.model.request.people.EditEmployeeRequest;
import com.jupiter.asclepi.core.service.api.EmployeeService;
import com.jupiter.asclepi.core.utils.ConstraintDocumentationHelper;
import com.jupiter.asclepi.core.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@Import(TestHelperConfiguration.class)
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

    private static final FieldDescriptor[] CREATE_EMPLOYEE_REQUEST_FIELD_DESCRIPTORS = generateCreateEmployeeRequestDescriptors();
    private static final FieldDescriptor[] EDIT_EMPLOYEE_REQUEST_FIELD_DESCRIPTORS = generateEditEmployeeRequestDescriptors();
    private static final FieldDescriptor[] ERROR_INFO_FIELD_DESCRIPTORS = new FieldDescriptor[]{
            fieldWithPath("message").description("Error message").type(JsonFieldType.STRING)
    };

    private MockMvc mockMvc;
    private final EmployeeTestHelper helper;
    private final EmployeeService service;

    @Autowired
    public EmployeeControllerSignaturesTest(EmployeeTestHelper helper, EmployeeService service) {
        this.helper = helper;
        this.service = service;
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = TestUtils.createMockMvcDefaultConfiguration(webApplicationContext, restDocumentation);
    }

    @Test
    void testSuccessfulEmployeeCreationRequestResponseSignatures() throws Exception {
        this.mockMvc.perform(helper.createMockedCreateRequest(helper.generateCreateRequest(false)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("employeeSuccessfulCreation",
                        requestFields(CREATE_EMPLOYEE_REQUEST_FIELD_DESCRIPTORS),
                        responseFields(EMPLOYEE_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testFailedDueToExistingLoginEmployeeCreationRequestResponseSignature() throws Exception {
        CreateEmployeeRequest request = helper.generateCreateRequest(false);
        service.create(request).get();
        this.mockMvc.perform(helper.createMockedCreateRequest(request))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("employeeExistingLoginCreation",
                        requestFields(CREATE_EMPLOYEE_REQUEST_FIELD_DESCRIPTORS),
                        responseFields(ERROR_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testSuccessfulEmployeeEditingRequestResponseSignatures() throws Exception {
        CreateEmployeeRequest request = helper.generateCreateRequest(false);
        Employee created = service.create(request).get();
        this.mockMvc.perform(helper.createMockedEditRequest(helper.generateEditRequest(created.getId(), true)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("employeeSuccessfulEdition",
                        requestFields(EDIT_EMPLOYEE_REQUEST_FIELD_DESCRIPTORS),
                        responseFields(EMPLOYEE_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testFailedDueToNonExistentEmployeeEditingRequestResponseSignatures() throws Exception {
        this.mockMvc.perform(helper.createMockedEditRequest(helper.generateEditRequest(0, false)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("employeeNonExistentEdition",
                        requestFields(EDIT_EMPLOYEE_REQUEST_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testFailedDueToExistingLoginEmployeeEditingRequestResponseSignatures() throws Exception {
        CreateEmployeeRequest request = helper.generateCreateRequest(false);
        Employee one = service.create(request).get();
        Employee another = service.create(helper.generateAnotherCreateRequest(request)).get();
        EditEmployeeRequest editRequest = helper.generateEditRequest(one.getId(), true);
        editRequest.setLogin(another.getLogin());
        this.mockMvc.perform(helper.createMockedEditRequest(editRequest))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        requestFields(EDIT_EMPLOYEE_REQUEST_FIELD_DESCRIPTORS),
                        responseFields(ERROR_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testSuccessfulEmployeeGettingRequestResponseSignatures() throws Exception {
        CreateEmployeeRequest request = helper.generateCreateRequest(false);
        Employee created = service.create(request).get();
        this.mockMvc.perform(helper.createMockedGetRequest(created.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("employeeSuccessfulGetting",
                        pathParameters(parameterWithName("employeeId").description("ID of the existing employee")),
                        responseFields(EMPLOYEE_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testNonExistentEmployeeGettingRequestResponseSignatures() throws Exception {
        this.mockMvc.perform(helper.createMockedGetRequest(0))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("employeeNonExistentGetting",
                        pathParameters(parameterWithName("employeeId").description("ID of the existing employee"))
                ));
    }

    @Test
    void testSuccessfulAllEmployeesGettingRequestResponseSignatures() throws Exception {
        CreateEmployeeRequest request = helper.generateCreateRequest(false);
        service.create(request).get();
        this.mockMvc.perform(helper.createMockedGetAllRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("employeeSuccessfulGettingAll",
                        responseFields(fieldWithPath("[]").description("Array of EmployeeInfo").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[]", EMPLOYEE_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testSuccessfulEmployeeDeletionRequestResponseSignatures() throws Exception {
        CreateEmployeeRequest request = helper.generateCreateRequest(false);
        Employee created = service.create(request).get();
        this.mockMvc.perform(helper.createMockedDeleteRequest(created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("employeeSuccessfulDeletion",
                        pathParameters(parameterWithName("employeeId").description("ID of the existing employee"))
                ));
    }

    @Test
    void testNonExistentEmployeeDeletionRequestResponseSignatures() throws Exception {
        this.mockMvc.perform(helper.createMockedDeleteRequest(0))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("employeeNonExistentDeletion",
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
                        .description("Login of the edited employee").type(JsonFieldType.STRING).optional(),
                constraintDocumentationHelper.fieldDescriptorFor("password")
                        .description("Password of the edited employee").type(JsonFieldType.STRING).optional(),
                constraintDocumentationHelper.fieldDescriptorFor("role")
                        .description("Role id of the edited employee").type(JsonFieldType.NUMBER).optional(),
                constraintDocumentationHelper.fieldDescriptorFor("name")
                        .description("Name of the edited employee").type(JsonFieldType.STRING).optional(),
                constraintDocumentationHelper.fieldDescriptorFor("surname")
                        .description("Surname of the edited employee").type(JsonFieldType.STRING).optional(),
                constraintDocumentationHelper.fieldDescriptorFor("middleName")
                        .description("Middle name of the edited employee").type(JsonFieldType.STRING).optional(),
                constraintDocumentationHelper.fieldDescriptorFor("additionalInfo")
                        .description("Additional info about the edited employee").type(JsonFieldType.STRING).optional()
        };
    }
}
