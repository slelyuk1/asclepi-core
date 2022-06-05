package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.ClientTestHelper;
import com.jupiter.asclepi.core.helper.DiseaseHistoryTestHelper;
import com.jupiter.asclepi.core.helper.EmployeeTestHelper;
import com.jupiter.asclepi.core.model.request.employee.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.repository.entity.employee.Role;
import com.jupiter.asclepi.core.service.api.ClientService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
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
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigInteger;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@Import(TestHelperConfiguration.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class DiseaseHistoryControllerTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EmployeeTestHelper employeeHelper;
    @Autowired
    private ClientTestHelper clientHelper;
    @Autowired
    private DiseaseHistoryTestHelper diseaseHistoryHelper;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private DiseaseHistoryService diseaseHistoryService;

    private MockMvc mockMvc;
    private Employee existingDoctor;
    private Client existingClient;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = TestUtils.createMockMvcDefaultConfiguration(webApplicationContext, restDocumentation);
        existingDoctor = employeeService.create(employeeHelper.generateCreateRequest(true, Role.DOCTOR));
        existingClient = clientService.create(clientHelper.generateCreateRequest(true));
    }

    @Test
    void testSuccessfulCreationRequestResponseSignatures() throws Exception {
        CreateDiseaseHistoryRequest request = diseaseHistoryHelper.generateCreateRequest(existingClient.getId(), existingDoctor.getId());
        this.mockMvc.perform(diseaseHistoryHelper.createMockedCreatedRequest(request))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("diseaseHistorySuccessfulCreation",
                        generateCreateRequest(),
                        generateInfoResponse()
                ));
    }

    @Test
    void testSuccessfulEditingRequestResponseSignatures() throws Exception {
        CreateEmployeeRequest createEmployeeRequest = employeeHelper.generateAnotherCreateRequest(employeeHelper.generateCreateRequest(false));
        createEmployeeRequest.setRoleId(Role.DOCTOR.getId());
        Employee anotherDoctor = employeeService.create(createEmployeeRequest);
        DiseaseHistory created = diseaseHistoryService
                .create(diseaseHistoryHelper.generateCreateRequest(existingClient.getId(), existingDoctor.getId()));
        this.mockMvc.perform(diseaseHistoryHelper.createMockedEditRequest(diseaseHistoryHelper.generateEditRequest(created, anotherDoctor.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("diseaseHistorySuccessfulEdition",
                        generateEditRequest(),
                        generateInfoResponse()
                ));
    }

    @Test
    void testFailedDueToNonExistentEditingRequestResponseSignatures() throws Exception {
        EditDiseaseHistoryRequest request = new EditDiseaseHistoryRequest();
        GetDiseaseHistoryRequest getter = new GetDiseaseHistoryRequest();
        getter.setClientId(BigInteger.ZERO);
        getter.setNumber(0);
        request.setDoctorId(0);
        request.setDiseaseHistory(getter);
        this.mockMvc.perform(diseaseHistoryHelper.createMockedEditRequest(request))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("diseaseHistoryNonExistentEdition",
                        generateEditRequest()
                ));
    }

    @Test
    void testSuccessfulGettingRequestResponseSignatures() throws Exception {
        DiseaseHistory created = diseaseHistoryService
                .create(diseaseHistoryHelper.generateCreateRequest(existingClient.getId(), existingDoctor.getId()));
        this.mockMvc.perform(diseaseHistoryHelper.createMockedGetRequest(created.getClient().getId(), created.getId().getNumber()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("diseaseHistorySuccessfulGetting",
                        generateGetRequest(),
                        generateInfoResponse()
                ));
    }

    @Test
    void testNonExistentGettingRequestResponseSignatures() throws Exception {
        this.mockMvc.perform(diseaseHistoryHelper.createMockedGetRequest(BigInteger.ZERO, 0))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("diseaseHistoryNonExistentGetting",
                        generateGetRequest()
                ));
    }

    @Test
    void testSuccessfulAllGettingRequestResponseSignatures() throws Exception {
        diseaseHistoryService.create(diseaseHistoryHelper.generateCreateRequest(existingClient.getId(), existingDoctor.getId()));
        this.mockMvc.perform(diseaseHistoryHelper.createMockedGetAllRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("diseaseHistorySuccessfulGettingAll",
                        responseFields(fieldWithPath("[]").description("Array of DiseaseHistory").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[].", generateInfoDescriptors())
                                .andWithPrefix("[].diseaseHistory.", generateGetRequestDescriptors())
                ));
    }

    @Test
    void testSuccessfulGettingForClientRequestResponseSignatures() throws Exception {
        DiseaseHistory created = diseaseHistoryService
                .create(diseaseHistoryHelper.generateCreateRequest(existingClient.getId(), existingDoctor.getId()));
        this.mockMvc.perform(diseaseHistoryHelper.createMockedGetForClientRequest(created.getClient().getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("diseaseHistorySuccessfulGettingForClient",
                        responseFields(fieldWithPath("[]").description("Array of DiseaseHistory").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[].", generateInfoDescriptors())
                                .andWithPrefix("[].diseaseHistory.", generateGetRequestDescriptors())
                ));
    }

    public static ResponseFieldsSnippet generateInfoResponse() {
        return responseFields(generateInfoDescriptors())
                .andWithPrefix("diseaseHistory.", generateGetRequestDescriptors());
    }

    public static RequestFieldsSnippet generateCreateRequest() {
        return requestFields(generateCreateRequestDescriptors());
    }

    public static RequestFieldsSnippet generateEditRequest() {
        return requestFields(generateEditRequestDescriptors())
                .andWithPrefix("diseaseHistory.", generateGetRequestDescriptors());
    }

    public static RequestFieldsSnippet generateGetRequest() {
        return requestFields(generateGetRequestDescriptors());
    }

    private static FieldDescriptor[] generateInfoDescriptors() {
        return new FieldDescriptor[]{
                fieldWithPath("diagnosisIds").description("IDs of diagnoses linked to the disease history.").type(JsonFieldType.ARRAY),
                fieldWithPath("doctorId").description("ID of the doctor which leads this history.").type(JsonFieldType.NUMBER)
        };
    }

    public static FieldDescriptor[] generateGetRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(GetDiseaseHistoryRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("clientId")
                        .description("ID of the client to which the created history will belong.").type(JsonFieldType.NUMBER),
                docHelper.fieldDescriptorFor("number")
                        .description("Number of the disease history.").type(JsonFieldType.NUMBER)
        };
    }

    private static FieldDescriptor[] generateCreateRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(CreateDiseaseHistoryRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("clientId")
                        .description("ID of the client to which the created history will belong.").type(JsonFieldType.NUMBER),
                docHelper.fieldDescriptorFor("doctorId")
                        .description("ID of the doctor which leads this history.").type(JsonFieldType.NUMBER)
        };
    }

    private static FieldDescriptor[] generateEditRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(EditDiseaseHistoryRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("doctorId")
                        .description("ID of the doctor which leads this history.").type(JsonFieldType.NUMBER).optional()
        };
    }
}
