package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.ClientTestHelper;
import com.jupiter.asclepi.core.helper.DiseaseHistoryTestHelper;
import com.jupiter.asclepi.core.helper.EmployeeTestHelper;
import com.jupiter.asclepi.core.helper.VisitTestHelper;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.request.client.CreateClientRequest;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.repository.entity.employee.Role;
import com.jupiter.asclepi.core.service.api.ClientService;
import com.jupiter.asclepi.core.service.api.DiseaseHistoryService;
import com.jupiter.asclepi.core.service.api.EmployeeService;
import com.jupiter.asclepi.core.service.api.VisitService;
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

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@Import(TestHelperConfiguration.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class VisitControllerTest {
    @Autowired
    private EmployeeTestHelper employeeHelper;
    @Autowired
    private ClientTestHelper clientHelper;
    @Autowired
    private DiseaseHistoryTestHelper diseaseHistoryHelper;
    @Autowired
    private VisitTestHelper visitHelper;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private DiseaseHistoryService diseaseHistoryService;
    @Autowired
    private VisitService visitService;

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
        CreateVisitRequest request = visitHelper.generateCreateRequest(existingHistory);
        this.mockMvc.perform(visitHelper.createMockedCreateRequest(request))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("visitSuccessfulCreation",
                        generateCreateRequest(),
                        generateInfoResponse()
                ));
    }

    @Test
    void testSuccessfulEditingRequestResponseSignatures() throws Exception {
        Visit created = visitService.create(visitHelper.generateCreateRequest(existingHistory)).get();
        EditVisitRequest request = visitHelper.generateEditRequest(created, created.getWhen().plusDays(1));
        this.mockMvc.perform(visitHelper.createMockedEditRequest(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("visitSuccessfulEdition",
                        generateEditRequest(),
                        generateInfoResponse()
                ));
    }

    @Test
    void testFailedDueToNonExistentEditingRequestResponseSignatures() throws Exception {
        EditVisitRequest request = new EditVisitRequest();
        GetDiseaseHistoryRequest getter = new GetDiseaseHistoryRequest();
        getter.setClientId(BigInteger.ZERO);
        getter.setNumber(0);
        GetVisitRequest visitGetter = new GetVisitRequest();
        visitGetter.setDiseaseHistory(getter);
        visitGetter.setNumber(0);
        request.setVisit(visitGetter);
        request.setWhen(LocalDateTime.now().plusDays(1));
        this.mockMvc.perform(visitHelper.createMockedEditRequest(request))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("visitNonExistentEdition",
                        generateEditRequest()
                ));
    }

    @Test
    void testSuccessfulGettingRequestResponseSignatures() throws Exception {
        Visit created = visitService.create(visitHelper.generateCreateRequest(existingHistory)).get();
        GetVisitRequest getter = new GetVisitRequest();
        getter.setNumber(created.getId().getNumber());
        GetDiseaseHistoryRequest historyGetter = new GetDiseaseHistoryRequest();
        historyGetter.setClientId(created.getDiseaseHistory().getClient().getId());
        historyGetter.setNumber(created.getDiseaseHistory().getId().getNumber());
        getter.setDiseaseHistory(historyGetter);
        this.mockMvc.perform(visitHelper.createMockedGetRequest(getter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("visitSuccessfulGetting",
                        pathParameters(
                                parameterWithName("clientId").description("ID of the existing client."),
                                parameterWithName("diseaseHistoryNumber").description("ID of the existing disease history."),
                                parameterWithName("number").description("Number of the existing visit.")
                        ),
                        generateInfoResponse()
                ));
    }

    @Test
    void testNonExistentGettingRequestResponseSignatures() throws Exception {
        GetVisitRequest request = new GetVisitRequest();
        request.setNumber(0);
        GetDiseaseHistoryRequest historyGetter = new GetDiseaseHistoryRequest();
        historyGetter.setNumber(0);
        historyGetter.setClientId(BigInteger.ZERO);
        request.setDiseaseHistory(historyGetter);
        this.mockMvc.perform(visitHelper.createMockedGetRequest(request))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("visitNonExistentGetting",
                        pathParameters(
                                parameterWithName("clientId").description("ID of the existing client."),
                                parameterWithName("diseaseHistoryNumber").description("ID of the existing disease history."),
                                parameterWithName("number").description("Number of the existing visit.")
                        )
                ));
    }

    @Test
    void testSuccessfulAllGettingRequestResponseSignatures() throws Exception {
        visitService.create(visitHelper.generateCreateRequest(existingHistory)).get();
        this.mockMvc.perform(visitHelper.createMockedGetAllRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("visitSuccessfulGettingAll",
                        responseFields(fieldWithPath("[]").description("Array of DiseaseHistory").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[].", generateInfoFieldDescriptor())
                                .andWithPrefix("[].visit.", generateGetRequestDescriptors())
                                .andWithPrefix("[].visit.diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors())
                ));
    }

    @Test
    void testSuccessfulGettingForDiseaseHistoryRequestResponseSignatures() throws Exception {
        Visit created = visitService.create(visitHelper.generateCreateRequest(existingHistory)).get();
        GetDiseaseHistoryRequest request = new GetDiseaseHistoryRequest();
        request.setClientId(created.getDiseaseHistory().getClient().getId());
        request.setNumber(created.getDiseaseHistory().getId().getNumber());
        this.mockMvc.perform(visitHelper.createMockedGetForDiseaseHistory(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("visitSuccessfulGettingForDiseaseHistory",
                        pathParameters(
                                parameterWithName("clientId").description("ID of the existing client."),
                                parameterWithName("diseaseHistoryNumber").description("ID of the existing disease history.")
                        ),
                        responseFields(fieldWithPath("[]").description("Array of DiseaseHistory").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[].", generateInfoFieldDescriptor())
                                .andWithPrefix("[].visit.", generateGetRequestDescriptors())
                                .andWithPrefix("[].visit.diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors())
                ));
    }

    private static ResponseFieldsSnippet generateInfoResponse() {
        return responseFields(generateInfoFieldDescriptor())
                .andWithPrefix("visit.", generateGetRequestDescriptors())
                .andWithPrefix("visit.diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors());
    }

    private static RequestFieldsSnippet generateCreateRequest() {
        return requestFields(generateCreateRequestDescriptors())
                .andWithPrefix("diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors());
    }

    private static RequestFieldsSnippet generateEditRequest() {
        return requestFields(generateEditRequestDescriptors())
                .andWithPrefix("visit.", generateGetRequestDescriptors())
                .andWithPrefix("visit.diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors());
    }

    public static RequestFieldsSnippet generateGetRequest() {
        return requestFields(generateGetRequestDescriptors())
                .andWithPrefix("diseaseHistory.", DiseaseHistoryControllerTest.generateGetRequestDescriptors());
    }

    private static FieldDescriptor[] generateInfoFieldDescriptor() {
        return new FieldDescriptor[]{
                fieldWithPath("when").description("When visit will be held.").type(JsonFieldType.STRING)
        };
    }

    public static FieldDescriptor[] generateGetRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(GetVisitRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("number").description("Number of visit.").type(JsonFieldType.NUMBER)
        };
    }

    private static FieldDescriptor[] generateCreateRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(CreateClientRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("when")
                        .description("Date and time when this disease history is held.").type(JsonFieldType.STRING)
        };
    }

    private static FieldDescriptor[] generateEditRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(CreateClientRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("when")
                        .description("Date and time when this disease history is held.").type(JsonFieldType.STRING).optional()
        };
    }
}
