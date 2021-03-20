package com.jupiter.asclepi.core.rest.controller.impl.consultation;

import com.jupiter.asclepi.core.helper.*;
import com.jupiter.asclepi.core.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.entity.disease.Consultation;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.other.Role;
import com.jupiter.asclepi.core.model.request.disease.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.rest.controller.impl.diseaseHistory.DiseaseHistoryControllerSignaturesTest;
import com.jupiter.asclepi.core.rest.controller.impl.visit.VisitControllerSignaturesTest;
import com.jupiter.asclepi.core.service.*;
import com.jupiter.asclepi.core.utils.ConstraintDocumentationHelper;
import com.jupiter.asclepi.core.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Disabled
class ConsultationControllerSignaturesTest {
    @Autowired
    private EmployeeTestHelper employeeHelper;
    @Autowired
    private ClientTestHelper clientHelper;
    @Autowired
    private DiseaseHistoryTestHelper diseaseHistoryHelper;
    @Autowired
    private VisitTestHelper visitHelper;
    @Autowired
    private AnamnesisTestHelper anamnesisHelper;
    @Autowired
    private ConsultationTestHelper consultationHelper;

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
    @Autowired
    private AnamnesisService anamnesisService;
    @Autowired
    private ConsultationService consultationService;

    private MockMvc mockMvc;
    private Visit existingVisit;
    private Anamnesis existingAnamnesis;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = TestUtils.createMockMvcDefaultConfiguration(webApplicationContext, restDocumentation);
        Employee doctor = employeeService.create(employeeHelper.generateCreateRequest(true, Role.DOCTOR)).get();
        Client client = clientService.create(clientHelper.generateCreateRequest(true)).get();
        DiseaseHistory history = diseaseHistoryService.create(diseaseHistoryHelper.generateCreateRequest(client.getId(), doctor.getId())).get();
        existingVisit = visitService.create(visitHelper.generateCreateRequest(history)).get();
        existingAnamnesis = anamnesisService.create(anamnesisHelper.generateCreateRequest(history)).get();
    }

    @Test
    void testSuccessfulCreationRequestResponseSignatures() throws Exception {
        CreateConsultationRequest request = consultationHelper.generateCreateRequest(existingVisit, existingAnamnesis);
        this.mockMvc.perform(consultationHelper.createMockedCreateRequest(request))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("consultationSuccessfulCreation",
                        generateCreateRequest(),
                        generateInfoResponse()
                ));
    }

    @Test
    void testSuccessfulEditingRequestResponseSignatures() throws Exception {
        Consultation created = consultationService.create(consultationHelper.generateCreateRequest(existingVisit, existingAnamnesis)).get();
        EditConsultationRequest request = consultationHelper.generateEditRequest(created);
        this.mockMvc.perform(consultationHelper.createMockedEditRequest(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("consultationSuccessfulEdition",
                        generateEditRequest(),
                        generateInfoResponse()
                ));
    }

    @Test
    void testFailedDueToNonExistentEditingRequestResponseSignatures() throws Exception {
        EditConsultationRequest request = new EditConsultationRequest();
        GetConsultationRequest getter = new GetConsultationRequest(
                new GetVisitRequest(new GetDiseaseHistoryRequest(BigInteger.ZERO, 0), 0),
                0
        );
        request.setAnamnesisId(BigInteger.ZERO);
        request.setInspection("testInspection");
        this.mockMvc.perform(consultationHelper.createMockedEditRequest(request))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("consultationNonExistentEdition",
                        generateEditRequest()
                ));
    }

    @Test
    void testSuccessfulGettingRequestResponseSignatures() throws Exception {
        Consultation created = consultationService.create(consultationHelper.generateCreateRequest(existingVisit, existingAnamnesis)).get();
        DiseaseHistory history = created.getVisit().getDiseaseHistory();
        GetConsultationRequest request = new GetConsultationRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getNumber()),
                        created.getVisit().getNumber()
                ),
                created.getNumber()
        );
        this.mockMvc.perform(consultationHelper.createMockedGetRequest(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("consultationSuccessfulGetting",
                        generateGetRequest(),
                        generateInfoResponse()
                ));
    }

    @Test
    void testNonExistentGettingRequestResponseSignatures() throws Exception {
        GetConsultationRequest getter = new GetConsultationRequest(
                new GetVisitRequest(new GetDiseaseHistoryRequest(BigInteger.ZERO, 0), 0),
                0
        );
        this.mockMvc.perform(consultationHelper.createMockedGetRequest(getter))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("consultationNonExistentGetting",
                        generateGetRequest()
                ));
    }

    @Test
    void testSuccessfulDeletionRequestResponseSignatures() throws Exception {
        Consultation created = consultationService.create(consultationHelper.generateCreateRequest(existingVisit, existingAnamnesis)).get();
        DiseaseHistory history = created.getVisit().getDiseaseHistory();
        GetConsultationRequest request = new GetConsultationRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getNumber()),
                        created.getVisit().getNumber()
                ),
                created.getNumber()
        );
        this.mockMvc.perform(consultationHelper.createMockedDeleteRequest(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("ConsultationSuccessfulDeletion",
                        generateGetRequest(),
                        generateInfoResponse()
                ));
    }

    @Test
    void testSuccessfulAllGettingRequestResponseSignatures() throws Exception {
        consultationService.create(consultationHelper.generateCreateRequest(existingVisit, existingAnamnesis)).get();
        this.mockMvc.perform(consultationHelper.createMockedGetAllRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("consultationSuccessfulGettingAll",
                        responseFields(fieldWithPath("[]").description("Array of Consultation.").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[].", generateInfoFieldDescriptor())
                                .andWithPrefix("[].visit.", generateGetRequestDescriptors())
                                .andWithPrefix("[].visit.diseaseHistory.", DiseaseHistoryControllerSignaturesTest.generateGetRequestDescriptors())
                ));
    }

    @Test
    void testSuccessfulGettingForVisitRequestResponseSignatures() throws Exception {
        Consultation created = consultationService.create(consultationHelper.generateCreateRequest(existingVisit, existingAnamnesis)).get();
        DiseaseHistory history = created.getVisit().getDiseaseHistory();
        GetVisitRequest request = new GetVisitRequest(
                new GetDiseaseHistoryRequest(history.getClient().getId(), history.getNumber()),
                created.getVisit().getNumber()
        );
        this.mockMvc.perform(consultationHelper.createMockedGetForVisit(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("consultationSuccessfulGettingForVisit",
                        VisitControllerSignaturesTest.generateGetRequest(),
                        responseFields(fieldWithPath("[]").description("Array of Consultation.").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[].", generateInfoFieldDescriptor())
                                .andWithPrefix("[].visit.", generateGetRequestDescriptors())
                                .andWithPrefix("[].visit.diseaseHistory.", DiseaseHistoryControllerSignaturesTest.generateGetRequestDescriptors())
                ));
    }

    @Test
    void testSuccessfulGettingForDiseaseHistoryRequestResponseSignatures() throws Exception {
        Consultation created = consultationService.create(consultationHelper.generateCreateRequest(existingVisit, existingAnamnesis)).get();
        DiseaseHistory history = created.getVisit().getDiseaseHistory();
        GetDiseaseHistoryRequest request = new GetDiseaseHistoryRequest(history.getClient().getId(), history.getNumber());
        this.mockMvc.perform(consultationHelper.createMockedGetForDiseaseHistory(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("consultationSuccessfulGettingForDiseaseHistory",
                        VisitControllerSignaturesTest.generateGetRequest(),
                        responseFields(fieldWithPath("[]").description("Array of Consultation.").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[].", generateInfoFieldDescriptor())
                                .andWithPrefix("[].visit.", generateGetRequestDescriptors())
                                .andWithPrefix("[].visit.diseaseHistory.", DiseaseHistoryControllerSignaturesTest.generateGetRequestDescriptors())
                ));
    }

    private static ResponseFieldsSnippet generateInfoResponse() {
        return responseFields(generateInfoFieldDescriptor())
                .andWithPrefix("consultation.", generateGetRequestDescriptors())
                .andWithPrefix("consultation.visit.", VisitControllerSignaturesTest.generateGetRequestDescriptors())
                .andWithPrefix("consultation.visit.diseaseHistory.", DiseaseHistoryControllerSignaturesTest.generateGetRequestDescriptors());
    }

    private static RequestFieldsSnippet generateCreateRequest() {
        return requestFields(generateCreateRequestDescriptors())
                .andWithPrefix("visit.", VisitControllerSignaturesTest.generateGetRequestDescriptors())
                .andWithPrefix("visit.diseaseHistory.", DiseaseHistoryControllerSignaturesTest.generateGetRequestDescriptors());
    }

    private static RequestFieldsSnippet generateEditRequest() {
        return requestFields(generateEditRequestDescriptors())
                .andWithPrefix("consultation.", generateGetRequestDescriptors())
                .andWithPrefix("consultation.visit.", VisitControllerSignaturesTest.generateGetRequestDescriptors())
                .andWithPrefix("consultation.visit.diseaseHistory.", DiseaseHistoryControllerSignaturesTest.generateGetRequestDescriptors());
    }

    private static RequestFieldsSnippet generateGetRequest() {
        return requestFields(generateGetRequestDescriptors())
                .andWithPrefix("visit.", VisitControllerSignaturesTest.generateGetRequestDescriptors())
                .andWithPrefix("visit.diseaseHistory.", DiseaseHistoryControllerSignaturesTest.generateGetRequestDescriptors());
    }

    private static FieldDescriptor[] generateInfoFieldDescriptor() {
        return new FieldDescriptor[]{
                fieldWithPath("anamnesisId").description("ID of the linked anamnesis.").type(JsonFieldType.STRING),
                fieldWithPath("inspection").description("Inspection gathered during consultation.").type(JsonFieldType.STRING)
        };
    }

    private static FieldDescriptor[] generateGetRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(GetConsultationRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("number").description("Number of consultation.").type(JsonFieldType.NUMBER)
        };
    }

    private static FieldDescriptor[] generateCreateRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(CreateConsultationRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("anamnesisId")
                        .description("ID of the linked anamnesis.").type(JsonFieldType.NUMBER),
                docHelper.fieldDescriptorFor("inspection")
                        .description("Inspection gathered during consultation.").type(JsonFieldType.STRING)
        };
    }

    private static FieldDescriptor[] generateEditRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(EditConsultationRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("anamnesisId")
                        .description("ID of the linked anamnesis.").type(JsonFieldType.NUMBER).optional(),
                docHelper.fieldDescriptorFor("inspection")
                        .description("Inspection gathered during consultation.").type(JsonFieldType.STRING).optional()
        };
    }
}
