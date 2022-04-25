package com.jupiter.asclepi.core.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.api.AbstractDocumentTest;
import com.jupiter.asclepi.core.model.model.entity.document.Document;
import com.jupiter.asclepi.core.model.model.request.disease.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.model.model.request.disease.document.EditDocumentRequest;
import com.jupiter.asclepi.core.utils.ConstraintDocumentationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
@Disabled
// todo refactor and fix
class DocumentControllerSignaturesTest extends AbstractDocumentTest {

    private static final FieldDescriptor[] INFO_FIELD_DESCRIPTORS = new FieldDescriptor[]{
            fieldWithPath("id").description("ID of the document in the system").type(JsonFieldType.NUMBER),
            //todo remove optional when diseaseHistory functionality is implemented
            fieldWithPath("diseaseHistoryId").description("Disease history id of the document in the system").type(JsonFieldType.STRING).optional(),
            //todo remove optional when diseaseHistory functionality is implemented
            fieldWithPath("analysisId").description("Analysis id of the document in the system").type(JsonFieldType.STRING).optional(),
            fieldWithPath("path").description("Path of the document in the system").type(JsonFieldType.STRING),
            fieldWithPath("description").description("Description of the document in the system").type(JsonFieldType.STRING).optional(),
    };

    private static final FieldDescriptor[] CREATE_REQUEST_FIELD_DESCRIPTORS = generateCreateRequestDescriptors();
    private static final FieldDescriptor[] EDIT_REQUEST_FIELD_DESCRIPTORS = generateEditRequestDescriptors();
    private static final FieldDescriptor[] ERROR_INFO_FIELD_DESCRIPTORS = new FieldDescriptor[]{
            fieldWithPath("message").description("Error message").type(JsonFieldType.STRING)
    };

    private MockMvc mockMvc;

    @Autowired
    public DocumentControllerSignaturesTest(ObjectMapper objectMapper, EntityManager entityManager) {
        super(objectMapper, entityManager);
    }

    private static FieldDescriptor[] generateCreateRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(CreateDocumentRequest.class);
        return new FieldDescriptor[]{
                //todo remove optional when diseaseHistory functionality is implemented
                docHelper.fieldDescriptorFor("diseaseHistoryId")
                        .description("Disease history id of the created document").type(JsonFieldType.STRING).optional(),
                //todo remove optional when diseaseHistory functionality is implemented
                docHelper.fieldDescriptorFor("analysisId")
                        .description("Analysis id of the created document").type(JsonFieldType.STRING).optional(),
                docHelper.fieldDescriptorFor("path").description("Path of the created document").type(JsonFieldType.STRING),
                docHelper.fieldDescriptorFor("description").description("Description of the created document").type(JsonFieldType.STRING).optional(),
        };
    }

    private static FieldDescriptor[] generateEditRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(EditDocumentRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("id").description("Id of the edited document").type(JsonFieldType.NUMBER),
                docHelper.fieldDescriptorFor("diseaseHistoryId")
                        .description("Disease history id of the created document").type(JsonFieldType.STRING).optional(),
                docHelper.fieldDescriptorFor("analysisId")
                        .description("Analysis id of the created document").type(JsonFieldType.STRING).optional(),
                docHelper.fieldDescriptorFor("path").description("Path of the created document").type(JsonFieldType.STRING).optional(),
                docHelper.fieldDescriptorFor("description").description("Description of the created document").type(JsonFieldType.STRING).optional(),
        };
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
        this.mockMvc.perform(generateCreateRequest(generateCreateParams(false)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        requestFields(CREATE_REQUEST_FIELD_DESCRIPTORS),
                        responseFields(INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testSuccessfulEditingRequestResponseSignatures() throws Exception {
        Document test = createTestEntity(true);
        getEntityManager().persist(test);
        this.mockMvc.perform(generateEditRequest(generateEditParams(test.getId(), false)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        requestFields(EDIT_REQUEST_FIELD_DESCRIPTORS),
                        responseFields(INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testFailedDueToNonExistentEditingRequestResponseSignatures() throws Exception {
        Document test = createTestEntity(false);
        getEntityManager().persist(test);
        this.mockMvc.perform(generateEditRequest(generateEditParams(BigInteger.ZERO, false)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("{method-name}",
                        requestFields(ERROR_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testSuccessfulGettingRequestResponseSignatures() throws Exception {
        Document test = createTestEntity(true);
        getEntityManager().persist(test);
        this.mockMvc.perform(generateGetRequest(test.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        pathParameters(parameterWithName("documentId").description("ID of the existing document")),
                        responseFields(INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testNonExistentGettingRequestResponseSignatures() throws Exception {
        this.mockMvc.perform(generateGetRequest(BigInteger.ZERO))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("{method-name}",
                        pathParameters(parameterWithName("documentId").description("ID of the existing document"))
                ));
    }

    @Test
    void testSuccessfulAllGettingRequestResponseSignatures() throws Exception {
        Document test = createTestEntity(true);
        getEntityManager().persist(test);
        this.mockMvc.perform(generateGetAllRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        responseFields(fieldWithPath("[]").description("Array of DocumentInfo").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[]", INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testSuccessfulDeletionRequestResponseSignatures() throws Exception {
        Document test = createTestEntity(true);
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
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("{method-name}",
                        pathParameters(parameterWithName("documentId").description("ID of the existing document"))
                ));
    }
}
