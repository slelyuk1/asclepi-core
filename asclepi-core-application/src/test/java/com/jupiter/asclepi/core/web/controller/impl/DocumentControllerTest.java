package com.jupiter.asclepi.core.web.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.*;
import com.jupiter.asclepi.core.helper.api.AbstractDocumentTest;
import com.jupiter.asclepi.core.model.request.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.model.request.document.EditDocumentRequest;
import com.jupiter.asclepi.core.repository.entity.Document;
import com.jupiter.asclepi.core.repository.entity.analysis.Analysis;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.repository.entity.employee.Role;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.service.api.*;
import com.jupiter.asclepi.core.utils.ConstraintDocumentationHelper;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@Import(TestHelperConfiguration.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
// todo refactor and fix
class DocumentControllerTest extends AbstractDocumentTest {

    private static final FieldDescriptor[] ERROR_INFO_FIELD_DESCRIPTORS = new FieldDescriptor[]{
            fieldWithPath("message").description("Error message").type(JsonFieldType.STRING)
    };

    private final EmployeeService employeeService;
    private final ClientService clientService;
    private final DiseaseHistoryService diseaseHistoryService;
    private final VisitService visitService;
    private final AnalysisService analysisService;
    private final EmployeeTestHelper employeeHelper;
    private final ClientTestHelper clientHelper;
    private final DiseaseHistoryTestHelper diseaseHistoryHelper;
    private final VisitTestHelper visitHelper;
    private final AnalysisTestHelper analysisHelper;

    private MockMvc mockMvc;
    private Analysis existingAnalysis;

    @Autowired
    public DocumentControllerTest(ObjectMapper objectMapper,
                                  EntityManager entityManager,
                                  EmployeeService employeeService,
                                  ClientService clientService,
                                  DiseaseHistoryService diseaseHistoryService,
                                  VisitService visitService,
                                  AnalysisService analysisService,
                                  EmployeeTestHelper employeeHelper,
                                  ClientTestHelper clientHelper,
                                  DiseaseHistoryTestHelper diseaseHistoryHelper,
                                  VisitTestHelper visitHelper,
                                  AnalysisTestHelper analysisHelper) {
        super(objectMapper, entityManager);
        this.employeeService = employeeService;
        this.clientService = clientService;
        this.diseaseHistoryService = diseaseHistoryService;
        this.visitService = visitService;
        this.analysisService = analysisService;
        this.employeeHelper = employeeHelper;
        this.clientHelper = clientHelper;
        this.diseaseHistoryHelper = diseaseHistoryHelper;
        this.visitHelper = visitHelper;
        this.analysisHelper = analysisHelper;
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Employee doctor = employeeService.create(employeeHelper.generateCreateRequest(true, Role.DOCTOR));
        Client client = clientService.create(clientHelper.generateCreateRequest(true));
        DiseaseHistory history = diseaseHistoryService.create(diseaseHistoryHelper.generateCreateRequest(client.getId(), doctor.getId()));
        Visit visit = visitService.create(visitHelper.generateCreateRequest(history));
        existingAnalysis = analysisService.create(analysisHelper.generateCreateRequest(visit));
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    void testSuccessfulCreationRequestResponseSignatures() throws Exception {
        this.mockMvc.perform(generateCreateRequest(generateCreateParams(existingAnalysis, false)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        requestFields(generateCreateRequestDescriptors()),
                        responseFields(generateInfoFieldDescriptors())
                ));
    }

    @Test
    void testSuccessfulEditingRequestResponseSignatures() throws Exception {
        Document test = createTestEntity(existingAnalysis, true);
        getEntityManager().persist(test);
        this.mockMvc.perform(generateEditRequest(generateEditParams(test.getId(), false)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        requestFields(generateEditRequestDescriptors()),
                        responseFields(generateInfoFieldDescriptors())
                ));
    }

    @Test
    void testFailedDueToNonExistentEditingRequestResponseSignatures() throws Exception {
        Document test = createTestEntity(existingAnalysis, false);
        getEntityManager().persist(test);
        this.mockMvc.perform(generateEditRequest(generateEditParams(BigInteger.valueOf(-1), false)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("{method-name}",
                        requestFields(generateEditRequestDescriptors())
                ));
    }

    @Test
    void testSuccessfulGettingRequestResponseSignatures() throws Exception {
        Document test = createTestEntity(existingAnalysis, true);
        getEntityManager().persist(test);
        this.mockMvc.perform(generateGetRequest(test.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        pathParameters(parameterWithName("documentId").description("ID of the existing document")),
                        responseFields(generateInfoFieldDescriptors())
                ));
    }

    @Test
    void testNonExistentGettingRequestResponseSignatures() throws Exception {
        this.mockMvc.perform(generateGetRequest(BigInteger.ZERO))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("{method-name}",
                        pathParameters(parameterWithName("documentId").description("ID of the existing document"))
                ));
    }

    @Test
    void testSuccessfulAllGettingRequestResponseSignatures() throws Exception {
        Document test = createTestEntity(existingAnalysis, true);
        getEntityManager().persist(test);
        this.mockMvc.perform(generateGetAllRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        responseFields(fieldWithPath("[]").description("Array of DocumentInfo").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[].", generateInfoFieldDescriptors())
                ));
    }

    @Test
    void testSuccessfulDeletionRequestResponseSignatures() throws Exception {
        Document test = createTestEntity(existingAnalysis, true);
        getEntityManager().persist(test);
        this.mockMvc.perform(generateDeleteRequest(test.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("{method-name}",
                        pathParameters(parameterWithName("documentId").description("ID of the existing document"))
                ));
    }

    @Test
    void testNonExistentDeletionRequestResponseSignatures() throws Exception {
        this.mockMvc.perform(generateDeleteRequest(BigInteger.ZERO))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("{method-name}",
                        pathParameters(parameterWithName("documentId").description("ID of the existing document"))
                ));
    }

    private static List<FieldDescriptor> generateCreateRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(CreateDocumentRequest.class);
        List<FieldDescriptor> fieldDescriptors = new ArrayList<>( List.of(
                docHelper.fieldDescriptorFor("path").description("Path of the created document").type(JsonFieldType.STRING),
                docHelper.fieldDescriptorFor("description").description("Description of the created document").type(JsonFieldType.STRING).optional()
        ));
        fieldDescriptors.addAll(applyPathPrefix("analysis.", AnalysisControllerTest.generateGetRequestDescriptors()));
        return fieldDescriptors;
    }

    private static FieldDescriptor[] generateEditRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(EditDocumentRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("id").description("Id of the edited document").type(JsonFieldType.NUMBER),
                docHelper.fieldDescriptorFor("description").description("Description of the created document").type(JsonFieldType.STRING).optional(),
        };
    }

    // todo maybe use dochelper in responses too?
    private static List<FieldDescriptor> generateInfoFieldDescriptors() {
        List<FieldDescriptor> infoDescriptors = new ArrayList<>(List.of(
                fieldWithPath("id").description("ID of the document in the system").type(JsonFieldType.NUMBER),
                fieldWithPath("path").description("Path of the document in the system").type(JsonFieldType.STRING),
                fieldWithPath("description").description("Description of the document in the system").type(JsonFieldType.STRING).optional()
        ));
        infoDescriptors.addAll(applyPathPrefix("analysis.", AnalysisControllerTest.generateGetRequestDescriptors()));
        return infoDescriptors;
    }

}
