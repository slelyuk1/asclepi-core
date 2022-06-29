package com.jupiter.asclepi.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.*;
import com.jupiter.asclepi.core.helper.api.AbstractDocumentTest;
import com.jupiter.asclepi.core.model.response.DocumentInfo;
import com.jupiter.asclepi.core.repository.entity.Document;
import com.jupiter.asclepi.core.repository.entity.analysis.Analysis;
import com.jupiter.asclepi.core.repository.entity.analysis.AnalysisId;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistoryId;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.repository.entity.employee.Role;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.repository.entity.visit.VisitId;
import com.jupiter.asclepi.core.service.api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@Import(TestHelperConfiguration.class)
// todo refactor and fix
class DocumentServiceTest extends AbstractDocumentTest {

    private MockMvc mockMvc;
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

    private Analysis existingAnalysis;

    @Autowired
    public DocumentServiceTest(ObjectMapper objectMapper,
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

    @Test
    void testSuccessfulCreation() throws Exception {
        this.mockMvc.perform(generateCreateRequest(generateCreateParams(existingAnalysis, true)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    DocumentInfo info = getObjectMapper().readValue(result.getResponse().getContentAsString(), DocumentInfo.class);
                    Document created = getEntityManager().find(Document.class, info.id());
                    assertEntityIsEqualToInfo(created, info);
                });
    }

    @Test
    void testSuccessfulEditing() throws Exception {
        Document toEdit = createAnother(existingAnalysis, true);
        getEntityManager().persist(toEdit);

        this.mockMvc.perform(generateEditRequest(generateEditParams(toEdit.getId(), true)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    DocumentInfo info = getObjectMapper().readValue(result.getResponse().getContentAsString(), DocumentInfo.class);
                    assertEntityIsEqualToInfo(toEdit, info);
                });
    }

    @Test
    void testSuccessfulGetting() throws Exception {
        Document test = createTestEntity(existingAnalysis, false);
        getEntityManager().persist(test);
        this.mockMvc.perform(generateGetRequest(test.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    DocumentInfo info = getObjectMapper().readValue(result.getResponse().getContentAsString(), DocumentInfo.class);
                    assertEntityIsEqualToInfo(test, info);
                });
    }

    @Test
    void testSuccessfulGettingAll() throws Exception {
        Document one = createTestEntity(existingAnalysis, true);
        Document another = createAnother(existingAnalysis, true);
        getEntityManager().persist(one);
        getEntityManager().persist(another);
        this.mockMvc.perform(generateGetAllRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    DocumentInfo[] infos = getObjectMapper().readValue(result.getResponse().getContentAsString(), DocumentInfo[].class);
                    Assertions.assertEquals(2, infos.length);
                    DocumentInfo oneInfo = Arrays.stream(infos)
                            .filter(info -> Objects.equals(info.id(), one.getId()))
                            .findAny()
                            .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
                    DocumentInfo anotherInfo = Arrays.stream(infos)
                            .filter(info -> Objects.equals(info.id(), another.getId()))
                            .findAny()
                            .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
                    assertEntityIsEqualToInfo(one, oneInfo);
                    assertEntityIsEqualToInfo(another, anotherInfo);
                });
    }

    @Test
    void testSuccessfulDeletion() throws Exception {
        Document test = createTestEntity(existingAnalysis, true);
        getEntityManager().persist(test);
        this.mockMvc.perform(generateDeleteRequest(test.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(result -> {
                    Document found = getEntityManager().find(Document.class, test.getId());
                    Assertions.assertNull(found);
                });
    }

    @SuppressWarnings("SameParameterValue")
    private Document createAnother(Analysis analysis, boolean withOptional) {
        Document other = createTestEntity(analysis, withOptional);
        other.setPath(Paths.get("/otherFolder/otherFile.otherExt"));
        if (withOptional) {
            other.setDescription(other.getDescription() + "Other");
        }
        return other;
    }

    private void assertEntityIsEqualToInfo(Document entity, DocumentInfo info) {
        Assertions.assertEquals(entity.getId(), info.id());
        if (entity.getAnalysis() != null) {
            Assertions.assertEquals(entity.getAnalysis().getId(), new AnalysisId(
                    new VisitId(
                            new DiseaseHistoryId(
                                    info.analysis().getVisit().getDiseaseHistory().getClientId(),
                                    info.analysis().getVisit().getDiseaseHistory().getNumber()
                            ),
                            info.analysis().getVisit().getNumber()
                    ),
                    info.analysis().getNumber()
            ));
        }
        Assertions.assertEquals(entity.getDescription(), info.description());
    }
}
