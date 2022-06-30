package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.*;
import com.jupiter.asclepi.core.model.request.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.request.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.request.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.repository.entity.analysis.Analysis;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.repository.entity.employee.Role;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.service.api.*;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@WithMockUser
@SpringBootTest
@Import(TestHelperConfiguration.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class AnalysisControllerTest {
    @Autowired
    private EmployeeTestHelper employeeHelper;
    @Autowired
    private ClientTestHelper clientHelper;
    @Autowired
    private DiseaseHistoryTestHelper diseaseHistoryHelper;
    @Autowired
    private VisitTestHelper visitHelper;
    @Autowired
    private AnalysisTestHelper analysisHelper;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private DiseaseHistoryService diseaseHistoryService;
    @Autowired
    private VisitService visitService;
    @Autowired
    private AnalysisService analysisService;

    private MockMvc mockMvc;
    private Visit existingVisit;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = TestUtils.createMockMvcDefaultConfiguration(webApplicationContext, restDocumentation);
        Employee doctor = employeeService.create(employeeHelper.generateCreateRequest(true, Role.DOCTOR));
        Client client = clientService.create(clientHelper.generateCreateRequest(true));
        DiseaseHistory history = diseaseHistoryService.create(diseaseHistoryHelper.generateCreateRequest(client.getId(), doctor.getId()));
        existingVisit = visitService.create(visitHelper.generateCreateRequest(history));
    }

    @Test
    void testSuccessfulCreationRequestResponseSignatures() throws Exception {
        CreateAnalysisRequest request = analysisHelper.generateCreateRequest(existingVisit);
        this.mockMvc.perform(analysisHelper.createMockedCreateRequest(request))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("analysisSuccessfulCreation",
                        generateCreateRequest(),
                        generateInfoResponse()
                ));
    }

    @Test
    void testSuccessfulEditingRequestResponseSignatures() throws Exception {
        Analysis created = analysisService.create(analysisHelper.generateCreateRequest(existingVisit));
        EditAnalysisRequest request = analysisHelper.generateEditRequest(created);
        this.mockMvc.perform(analysisHelper.createMockedEditRequest(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("analysisSuccessfulEdition",
                        generateEditRequest(),
                        generateInfoResponse()
                ));
    }

    @Test
    void testFailedDueToNonExistentEditingRequestResponseSignatures() throws Exception {
        EditAnalysisRequest request = new EditAnalysisRequest();
        GetAnalysisRequest getter = new GetAnalysisRequest(
                new GetVisitRequest(new GetDiseaseHistoryRequest(BigInteger.ZERO, 0), 0),
                0
        );
        request.setAnalysis(getter);
        request.setTitle("testTitle");
        request.setSummary("testSummary");
        this.mockMvc.perform(analysisHelper.createMockedEditRequest(request))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("analysisNonExistentEdition",
                        generateEditRequest()
                ));
    }

    @Test
    void testSuccessfulGettingRequestResponseSignatures() throws Exception {
        Analysis created = analysisService.create(analysisHelper.generateCreateRequest(existingVisit));
        DiseaseHistory history = created.getVisit().getDiseaseHistory();
        GetAnalysisRequest request = new GetAnalysisRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getId().getNumber()),
                        created.getVisit().getId().getNumber()
                ),
                created.getId().getNumber()
        );
        this.mockMvc.perform(analysisHelper.createMockedGetRequest(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("analysisSuccessfulGetting",
                        generateGetRequest(),
                        generateInfoResponse()
                ));
    }

    @Test
    void testNonExistentGettingRequestResponseSignatures() throws Exception {
        GetAnalysisRequest getter = new GetAnalysisRequest(
                new GetVisitRequest(new GetDiseaseHistoryRequest(BigInteger.ZERO, 0), 0),
                0
        );
        this.mockMvc.perform(analysisHelper.createMockedGetRequest(getter))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("analysisNonExistentGetting",
                        generateGetRequest()
                ));
    }

    @Test
    void testSuccessfulDeletionRequestResponseSignatures() throws Exception {
        Analysis created = analysisService.create(analysisHelper.generateCreateRequest(existingVisit));
        DiseaseHistory history = created.getVisit().getDiseaseHistory();
        GetAnalysisRequest request = new GetAnalysisRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getId().getNumber()),
                        created.getVisit().getId().getNumber()
                ),
                created.getId().getNumber()
        );
        this.mockMvc.perform(analysisHelper.createMockedDeleteRequest(request))
                .andExpect(status().isOk())
                .andDo(document("analysisSuccessfulDeletion",
                        generateGetRequest()
                ));
    }

    @Test
    void testSuccessfulAllGettingRequestResponseSignatures() throws Exception {
        analysisService.create(analysisHelper.generateCreateRequest(existingVisit));
        this.mockMvc.perform(analysisHelper.createMockedGetAllRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("analysisSuccessfulGettingAll",
                        responseFields(fieldWithPath("[]").description("Array of analysis.").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[].", generateInfoFieldDescriptor())
                                .andWithPrefix("[].analysis.", generateGetRequestDescriptors())
                                .andWithPrefix("[].analysis.visit.", VisitControllerTest.generateGetRequestDescriptors())
                                .andWithPrefix("[].analysis.visit.diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors())
                ));
    }

    @Test
    void testSuccessfulGettingForVisitRequestResponseSignatures() throws Exception {
        Analysis created = analysisService.create(analysisHelper.generateCreateRequest(existingVisit));
        DiseaseHistory history = created.getVisit().getDiseaseHistory();
        GetVisitRequest request = new GetVisitRequest(
                new GetDiseaseHistoryRequest(history.getClient().getId(), history.getId().getNumber()),
                created.getVisit().getId().getNumber()
        );
        this.mockMvc.perform(analysisHelper.createMockedGetForVisit(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("analysisSuccessfulGettingForVisit",
                        VisitControllerTest.generateGetRequest(),
                        responseFields(fieldWithPath("[]").description("Array of analysis.").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[].", generateInfoFieldDescriptor())
                                .andWithPrefix("[].analysis.", generateGetRequestDescriptors())
                                .andWithPrefix("[].analysis.visit.", VisitControllerTest.generateGetRequestDescriptors())
                                .andWithPrefix("[].analysis.visit.diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors())
                ));
    }

    @Test
    void testSuccessfulGettingForDiseaseHistoryRequestResponseSignatures() throws Exception {
        Analysis created = analysisService.create(analysisHelper.generateCreateRequest(existingVisit));
        DiseaseHistory history = created.getVisit().getDiseaseHistory();
        GetDiseaseHistoryRequest request = new GetDiseaseHistoryRequest(history.getClient().getId(), history.getId().getNumber());
        this.mockMvc.perform(analysisHelper.createMockedGetForDiseaseHistory(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("analysisSuccessfulGettingForDiseaseHistory",
                        DiseaseHistoryControllerTest.generateGetRequest(),
                        responseFields(fieldWithPath("[]").description("Array of analysis.").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[].", generateInfoFieldDescriptor())
                                .andWithPrefix("[].analysis.", generateGetRequestDescriptors())
                                .andWithPrefix("[].analysis.visit.", VisitControllerTest.generateGetRequestDescriptors())
                                .andWithPrefix("[].analysis.visit.diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors())
                ));
    }

    private static ResponseFieldsSnippet generateInfoResponse() {
        return responseFields(generateInfoFieldDescriptor())
                .andWithPrefix("analysis.", generateGetRequestDescriptors())
                .andWithPrefix("analysis.visit.", VisitControllerTest.generateGetRequestDescriptors())
                .andWithPrefix("analysis.visit.diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors());
    }

    private static RequestFieldsSnippet generateCreateRequest() {
        return requestFields(generateCreateRequestDescriptors())
                .andWithPrefix("visit.", VisitControllerTest.generateGetRequestDescriptors())
                .andWithPrefix("visit.diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors());
    }

    private static RequestFieldsSnippet generateEditRequest() {
        return requestFields(generateEditRequestDescriptors())
                .andWithPrefix("analysis.", generateGetRequestDescriptors())
                .andWithPrefix("analysis.visit.", VisitControllerTest.generateGetRequestDescriptors())
                .andWithPrefix("analysis.visit.diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors());
    }

    private static RequestFieldsSnippet generateGetRequest() {
        return requestFields(generateGetRequestDescriptors())
                .andWithPrefix("visit.", VisitControllerTest.generateGetRequestDescriptors())
                .andWithPrefix("visit.diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors());
    }

    private static List<FieldDescriptor> generateInfoFieldDescriptor() {
        List<FieldDescriptor> infoDescriptors = new ArrayList<>(List.of(
                fieldWithPath("title").description("Title of analysis.").type(JsonFieldType.STRING),
                fieldWithPath("summary").description("Summary of analysis.").type(JsonFieldType.STRING),
                // todo descriptor for array elements
                fieldWithPath("documents").description("Documents linked to the analysis").type(JsonFieldType.ARRAY)
        ));
        infoDescriptors.addAll(applyPathPrefix("creation.", DiseaseHistoryControllerTest.generateCreationInfoDescriptors()));
        return infoDescriptors;
    }

    public static List<FieldDescriptor> generateGetRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(GetAnalysisRequest.class);
        List<FieldDescriptor> descriptors = new ArrayList<>(List.of(
                docHelper.fieldDescriptorFor("number").description("Number of analysis.").type(JsonFieldType.NUMBER)
        ));
        descriptors.addAll(applyPathPrefix("visit.", VisitControllerTest.generateGetRequestDescriptors()));
        descriptors.addAll(applyPathPrefix("visit.diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors()));
        return descriptors;
    }

    private static FieldDescriptor[] generateCreateRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(CreateAnalysisRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("title")
                        .description("Title of the created analysis.").type(JsonFieldType.STRING),
                docHelper.fieldDescriptorFor("summary")
                        .description("Summary of the created analysis.").type(JsonFieldType.STRING)
        };
    }

    private static FieldDescriptor[] generateEditRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(EditAnalysisRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("title")
                        .description("Title of the created analysis.").type(JsonFieldType.STRING).optional(),
                docHelper.fieldDescriptorFor("summary")
                        .description("Summary of the created analysis.").type(JsonFieldType.STRING).optional()
        };
    }
}
