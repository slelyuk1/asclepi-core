package com.jupiter.asclepi.core.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.api.AbstractDocumentTest;
import com.jupiter.asclepi.core.model.model.entity.document.Document;
import com.jupiter.asclepi.core.model.model.response.disease.DocumentInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
@Disabled
@Import(TestHelperConfiguration.class)
// todo refactor and fix
public class DocumentControllerBusinessTest extends AbstractDocumentTest {

    private MockMvc mockMvc;

    @Autowired
    public DocumentControllerBusinessTest(ObjectMapper objectMapper, EntityManager entityManager) {
        super(objectMapper, entityManager);
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testSuccessfulCreation() throws Exception {
        this.mockMvc.perform(generateCreateRequest(generateCreateParams(true)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    DocumentInfo info = getObjectMapper().readValue(result.getResponse().getContentAsString(), DocumentInfo.class);
                    Document created = getEntityManager().find(Document.class, info.getId());
                    assertEntityIsEqualToInfo(created, info);
                });
    }

    @Test
    void testSuccessfulEditing() throws Exception {
        Document toEdit = createAnother(true);
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
        Document test = createTestEntity(false);
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
        Document one = createTestEntity(true);
        Document another = createAnother(true);
        getEntityManager().persist(one);
        getEntityManager().persist(another);
        this.mockMvc.perform(generateGetAllRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    DocumentInfo[] infos = getObjectMapper().readValue(result.getResponse().getContentAsString(), DocumentInfo[].class);
                    Assertions.assertEquals(infos.length, 2);
                    DocumentInfo oneInfo = Arrays.stream(infos)
                            .filter(info -> Objects.equals(info.getId(), one.getId()))
                            .findAny()
                            .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
                    DocumentInfo anotherInfo = Arrays.stream(infos)
                            .filter(info -> Objects.equals(info.getId(), another.getId()))
                            .findAny()
                            .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
                    assertEntityIsEqualToInfo(one, oneInfo);
                    assertEntityIsEqualToInfo(another, anotherInfo);
                });
    }

    @Test
    void testSuccessfulDeletion() throws Exception {
        Document test = createTestEntity(true);
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
    private Document createAnother(boolean withOptional) {
        Document other = createTestEntity(withOptional);
        other.setPath(Paths.get("/otherFolder/otherFile.otherExt"));
        if (withOptional) {
            other.setDescription(other.getDescription() + "Other");
        }
        return other;
    }

    private void assertEntityIsEqualToInfo(Document entity, DocumentInfo info) {
        Assertions.assertEquals(entity.getId(), info.getId());
        // todo add when analysis and disease history functionality is implemented
//        if (entity.getAnalysis() != null) {
//            Assertions.assertEquals(entity.getAnalysis().getId(), info.getAnalysisId());
//        }
//        if (entity.getDiseaseHistory() != null) {
//            Assertions.assertEquals(BigInteger.valueOf(entity.getDiseaseHistory().getNumber()), info.getDiseaseHistoryId());
//        }
        Assertions.assertEquals(entity.getDescription(), info.getDescription());
    }
}
