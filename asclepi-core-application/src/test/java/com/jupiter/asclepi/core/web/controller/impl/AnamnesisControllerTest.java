package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.AnamnesisTestHelper;
import com.jupiter.asclepi.core.helper.ClientTestHelper;
import com.jupiter.asclepi.core.helper.DiseaseHistoryTestHelper;
import com.jupiter.asclepi.core.helper.EmployeeTestHelper;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.entity.Anamnesis;
import com.jupiter.asclepi.core.repository.entity.Client;
import com.jupiter.asclepi.core.repository.entity.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.Employee;
import com.jupiter.asclepi.core.repository.entity.other.Role;
import com.jupiter.asclepi.core.service.api.AnamnesisService;
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
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.math.BigInteger;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@Import(TestHelperConfiguration.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class AnamnesisControllerTest {
    @Autowired
    private EmployeeTestHelper employeeHelper;
    @Autowired
    private ClientTestHelper clientHelper;
    @Autowired
    private DiseaseHistoryTestHelper diseaseHistoryHelper;
    @Autowired
    private AnamnesisTestHelper anamnesisHelper;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private DiseaseHistoryService diseaseHistoryService;
    @Autowired
    private AnamnesisService anamnesisService;

    private MockMvc mockMvc;
    private DiseaseHistory existingHistory;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = TestUtils.createMockMvcDefaultConfiguration(webApplicationContext, restDocumentation);
        Employee doctor = employeeService.create(employeeHelper.generateCreateRequest(true, Role.DOCTOR)).get();
        Client client = clientService.create(clientHelper.generateCreateRequest(true)).get();
        existingHistory = diseaseHistoryService.create(diseaseHistoryHelper.generateCreateRequest(client.getId(), doctor.getId())).get();
    }

    @Test
    void testSuccessfulCreationRequestResponseSignatures() throws Exception {
        CreateAnamnesisRequest request = anamnesisHelper.generateCreateRequest(existingHistory);
        this.mockMvc.perform(anamnesisHelper.createMockedCreateRequest(request))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("anamnesisSuccessfulCreation",
                        generateCreateRequest(),
                        generateInfoResponse()
                ));
    }

    @Test
    void testSuccessfulGettingRequestResponseSignatures() throws Exception {
        Anamnesis created = anamnesisService.create(anamnesisHelper.generateCreateRequest(existingHistory)).get();
        this.mockMvc.perform(anamnesisHelper.createMockedGetRequest(created.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("anamnesisSuccessfulGetting",
                        pathParameters(parameterWithName("anamnesisId").description("ID of the existing anamnesis.")),
                        generateInfoResponse()
                ));
    }

    @Test
    void testNonExistentGettingRequestResponseSignatures() throws Exception {
        this.mockMvc.perform(anamnesisHelper.createMockedGetRequest(BigInteger.ZERO))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("anamnesisNonExistentGetting",
                        pathParameters(parameterWithName("anamnesisId").description("ID of the existing anamnesis."))
                ));
    }

    @Test
    void testSuccessfulEmployeeDeletionRequestResponseSignatures() throws Exception {
        Anamnesis created = anamnesisService.create(anamnesisHelper.generateCreateRequest(existingHistory)).get();
        this.mockMvc.perform(anamnesisHelper.createMockedDeleteRequest(created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("anamnesisSuccessfulDeletion",
                        pathParameters(parameterWithName("anamnesisId").description("ID of the existing anamnesis."))
                ));
    }

    @Test
    void testSuccessfulGettingForDiseaseHistoryRequestResponseSignatures() throws Exception {
        Anamnesis created = anamnesisService.create(anamnesisHelper.generateCreateRequest(existingHistory)).get();
        DiseaseHistory history = created.getDiseaseHistory();
        GetDiseaseHistoryRequest request = new GetDiseaseHistoryRequest(history.getClient().getId(), history.getId().getNumber());
        this.mockMvc.perform(anamnesisHelper.createMockedGetForDiseaseHistory(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcRestDocumentation.document("anamnesisSuccessfulGettingForDiseaseHistory",
                        DiseaseHistoryControllerTest.generateGetRequest(),
                        responseFields(fieldWithPath("[]").description("Array of anamnesis.").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[].", generateInfoFieldDescriptor())
                                .andWithPrefix("[].diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors())
                ));
    }

    private static ResponseFieldsSnippet generateInfoResponse() {
        return responseFields(generateInfoFieldDescriptor())
                .andWithPrefix("diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors());
    }

    private static RequestFieldsSnippet generateCreateRequest() {
        return requestFields(generateCreateRequestDescriptors())
                .andWithPrefix("diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors());
    }

    private static FieldDescriptor[] generateInfoFieldDescriptor() {
        return new FieldDescriptor[]{
                fieldWithPath("id").description("ID of the anamnesis.").type(JsonFieldType.NUMBER),
                fieldWithPath("complaints").description("Complaints of the client.").type(JsonFieldType.STRING),
                fieldWithPath("vitae").description("Past medical history of the client.").type(JsonFieldType.STRING),
                fieldWithPath("morbi").description("History of present disease of the client.").type(JsonFieldType.STRING)
        };
    }

    private static FieldDescriptor[] generateCreateRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(CreateAnamnesisRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("complaints")
                        .description("Complaints of the client.").type(JsonFieldType.STRING),
                docHelper.fieldDescriptorFor("vitae")
                        .description("Past medical history of the client.").type(JsonFieldType.STRING),
                docHelper.fieldDescriptorFor("morbi")
                        .description("History of present disease of the client.").type(JsonFieldType.STRING)
        };
    }
}
