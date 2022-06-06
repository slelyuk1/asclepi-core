package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.ClientTestHelper;
import com.jupiter.asclepi.core.helper.DiagnosisTestHelper;
import com.jupiter.asclepi.core.helper.DiseaseHistoryTestHelper;
import com.jupiter.asclepi.core.helper.EmployeeTestHelper;
import com.jupiter.asclepi.core.model.request.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.repository.entity.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.repository.entity.employee.Role;
import com.jupiter.asclepi.core.service.api.ClientService;
import com.jupiter.asclepi.core.service.api.DiagnosisService;
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
public class DiagnosisControllerTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EmployeeTestHelper employeeHelper;
    @Autowired
    private ClientTestHelper clientHelper;
    @Autowired
    private DiseaseHistoryTestHelper diseaseHistoryHelper;
    @Autowired
    private DiagnosisTestHelper diagnosisHelper;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private DiseaseHistoryService diseaseHistoryService;
    @Autowired
    private DiagnosisService diagnosisService;

    private MockMvc mockMvc;
    private DiseaseHistory existingHistory;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = TestUtils.createMockMvcDefaultConfiguration(webApplicationContext, restDocumentation);
        Employee doctor = employeeService.create(employeeHelper.generateCreateRequest(true, Role.DOCTOR));
        Client client = clientService.create(clientHelper.generateCreateRequest(true));
        existingHistory = diseaseHistoryService.create(diseaseHistoryHelper.generateCreateRequest(client.getId(), doctor.getId()));
    }

    @Test
    void testSuccessfulCreationRequestResponseSignatures() throws Exception {
        CreateDiagnosisRequest request = diagnosisHelper.generateCreateRequest(existingHistory, false);
        this.mockMvc.perform(diagnosisHelper.createMockedCreateRequest(request))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("diagnosisSuccessfulCreation",
                        generateCreateRequest(),
                        generateInfoResponse()
                ));
    }

    @Test
    void testSuccessfulEditingRequestResponseSignatures() throws Exception {
        Diagnosis created = diagnosisService.create(diagnosisHelper.generateCreateRequest(existingHistory, false));
        EditDiagnosisRequest request = diagnosisHelper.generateEditRequest(created, false);
        this.mockMvc.perform(diagnosisHelper.createMockedEditRequest(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("diagnosisSuccessfulEdition",
                        generateEditRequest(),
                        generateInfoResponse()
                ));
    }

    @Test
    void testFailedDueToNonExistentEditingRequestResponseSignatures() throws Exception {
        EditDiagnosisRequest request = new EditDiagnosisRequest();
        GetDiagnosisRequest getter = new GetDiagnosisRequest(new GetDiseaseHistoryRequest(BigInteger.ZERO, 0), 0);
        request.setDiagnosis(getter);
        this.mockMvc.perform(diagnosisHelper.createMockedEditRequest(request))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("diagnosisNonExistentEdition",
                        generateEditRequest()
                ));
    }

    @Test
    void testSuccessfulDeletionRequestResponseSignatures() throws Exception {
        Diagnosis created = diagnosisService.create(diagnosisHelper.generateCreateRequest(existingHistory, false));
        GetDiagnosisRequest getter = new GetDiagnosisRequest(
                new GetDiseaseHistoryRequest(existingHistory.getClient().getId(), existingHistory.getId().getNumber()),
                created.getId().getNumber()
        );
        this.mockMvc.perform(diagnosisHelper.createMockedDeleteRequest(getter))
                .andExpect(status().isOk())
                .andDo(document("diagnosisSuccessfulDeletion",
                        generateGetRequest()
                ));
    }

    @Test
    void testSuccessfulGettingRequestResponseSignatures() throws Exception {
        Diagnosis created = diagnosisService.create(diagnosisHelper.generateCreateRequest(existingHistory, true));
        GetDiagnosisRequest request = new GetDiagnosisRequest(
                new GetDiseaseHistoryRequest(existingHistory.getClient().getId(), existingHistory.getId().getNumber()),
                created.getId().getNumber()
        );
        this.mockMvc.perform(diagnosisHelper.createMockedGetRequest(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("diagnosisSuccessfulGetting",
                        generateGetRequest(),
                        generateInfoResponse()
                ));
    }

    @Test
    void testNonExistentGettingRequestResponseSignatures() throws Exception {
        GetDiagnosisRequest request = new GetDiagnosisRequest(new GetDiseaseHistoryRequest(BigInteger.ZERO, 0), 0);
        this.mockMvc.perform(diagnosisHelper.createMockedGetRequest(request))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("diagnosisNonExistentGetting",
                        generateGetRequest()
                ));
    }

    @Test
    void testSuccessfulAllGettingRequestResponseSignatures() throws Exception {
        diagnosisService.create(diagnosisHelper.generateCreateRequest(existingHistory, false));
        this.mockMvc.perform(diagnosisHelper.createMockedGetAllRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("diagnosisSuccessfulGettingAll",
                        responseFields(fieldWithPath("[]").description("Array of Diagnosis.").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[].", generateInfoFieldDescriptor())
                                .andWithPrefix("[].diagnosis.", generateGetRequestDescriptors())
                                .andWithPrefix("[].diagnosis.diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors())
                ));
    }

    @Test
    void testSuccessfulGettingForDiseaseHistoryRequestResponseSignatures() throws Exception {
        diagnosisService.create(diagnosisHelper.generateCreateRequest(existingHistory, false));
        GetDiseaseHistoryRequest request = new GetDiseaseHistoryRequest(existingHistory.getClient().getId(), existingHistory.getId().getNumber());
        this.mockMvc.perform(diagnosisHelper.createMockedGetForDiseaseHistory(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("diagnosisSuccessfulGettingForDiseaseHistory",
                        DiseaseHistoryControllerTest.generateGetRequest(),
                        responseFields(fieldWithPath("[]").description("Array of Diagnosis.").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[].", generateInfoFieldDescriptor())
                                .andWithPrefix("[].diagnosis.", generateGetRequestDescriptors())
                                .andWithPrefix("[].diagnosis.diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors())
                ));
    }

    private static ResponseFieldsSnippet generateInfoResponse() {
        return responseFields(generateInfoFieldDescriptor())
                .andWithPrefix("diagnosis.", generateGetRequestDescriptors())
                .andWithPrefix("diagnosis.diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors());
    }

    private static RequestFieldsSnippet generateCreateRequest() {
        return requestFields(generateCreateRequestDescriptors())
                .andWithPrefix("diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors());
    }

    private static RequestFieldsSnippet generateEditRequest() {
        return requestFields(generateEditRequestDescriptors())
                .andWithPrefix("diagnosis.", generateGetRequestDescriptors())
                .andWithPrefix("diagnosis.diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors());
    }

    public static RequestFieldsSnippet generateGetRequest() {
        return requestFields(generateGetRequestDescriptors())
                .andWithPrefix("diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors());
    }

    private static FieldDescriptor[] generateInfoFieldDescriptor() {
        return new FieldDescriptor[]{
                fieldWithPath("disease").description("Disease name.").type(JsonFieldType.STRING),
                fieldWithPath("isFinal").description("Is this diagnosis final for disease history.").type(JsonFieldType.BOOLEAN),
                fieldWithPath("complications").description("Complications.").type(JsonFieldType.STRING).optional(),
                fieldWithPath("etiologyAndPathogenesis").description("Etiology and pathogenesis.").type(JsonFieldType.STRING).optional(),
                fieldWithPath("specialityOfCourse").description("Speciality of curing course.").type(JsonFieldType.STRING).optional()
        };
    }

    public static FieldDescriptor[] generateGetRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(GetDiagnosisRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("number").description("Number of diagnosis.").type(JsonFieldType.NUMBER)
        };
    }

    private static FieldDescriptor[] generateCreateRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(CreateDiagnosisRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("disease").description("Disease name.").type(JsonFieldType.STRING),
                docHelper.fieldDescriptorFor("isFinal").description("Is this diagnosis final for disease history.").type(JsonFieldType.BOOLEAN),
                docHelper.fieldDescriptorFor("complications")
                        .description("Complications.").type(JsonFieldType.STRING).optional(),
                docHelper.fieldDescriptorFor("etiologyAndPathogenesis")
                        .description("Etiology and pathogenesis.").type(JsonFieldType.STRING).optional(),
                docHelper.fieldDescriptorFor("specialityOfCourse")
                        .description("Speciality of curing course.").type(JsonFieldType.STRING).optional()
        };
    }

    private static FieldDescriptor[] generateEditRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(EditDiagnosisRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("disease")
                        .description("Disease name.").type(JsonFieldType.STRING).optional(),
                docHelper.fieldDescriptorFor("isFinal")
                        .description("Is this diagnosis final for disease history.").type(JsonFieldType.BOOLEAN).optional(),
                docHelper.fieldDescriptorFor("complications")
                        .description("Complications.").type(JsonFieldType.STRING).optional(),
                docHelper.fieldDescriptorFor("etiologyAndPathogenesis")
                        .description("Etiology and pathogenesis.").type(JsonFieldType.STRING).optional(),
                docHelper.fieldDescriptorFor("specialityOfCourse")
                        .description("Speciality of curing course.").type(JsonFieldType.STRING).optional()
        };
    }
}
