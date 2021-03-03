package com.jupiter.asclepi.core.rest.controller.impl.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.people.EditClientRequest;
import com.jupiter.asclepi.core.utils.ConstraintDocumentationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class ClientControllerSignaturesTest extends AbstractClientTest {

    private static final FieldDescriptor[] CLIENT_INFO_FIELD_DESCRIPTORS = new FieldDescriptor[]{
            fieldWithPath("id").description("ID of the client in the system").type(JsonFieldType.NUMBER),
            fieldWithPath("name").description("Name of the client in the system").type(JsonFieldType.STRING),
            fieldWithPath("surname").description("Surname of the client in the system").type(JsonFieldType.STRING),
            fieldWithPath("middleName").description("Middle name of the client in the system").type(JsonFieldType.STRING).optional(),
            fieldWithPath("residence").description("Residence of the client in the system").type(JsonFieldType.STRING),
            fieldWithPath("gender").description("Gender of the client in the system (false = male, true = female)").type(JsonFieldType.BOOLEAN),
            fieldWithPath("job.name").description("Job name of the client in the system").type(JsonFieldType.STRING),
            fieldWithPath("job.organization").description("Job organization of the client in the system").type(JsonFieldType.STRING),
    };

    private static final FieldDescriptor[] CREATE_CLIENT_REQUEST_FIELD_DESCRIPTORS = generateCreateRequestDescriptors();
    private static final FieldDescriptor[] EDIT_CLIENT_REQUEST_FIELD_DESCRIPTORS = generateEditClientRequestDescriptors();
    private static final FieldDescriptor[] ERROR_INFO_FIELD_DESCRIPTORS = new FieldDescriptor[]{
            fieldWithPath("message").description("Error message").type(JsonFieldType.STRING)
    };

    private MockMvc mockMvc;

    @Autowired
    public ClientControllerSignaturesTest(ObjectMapper objectMapper, EntityManager entityManager) {
        super(objectMapper, entityManager);
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    void testSuccessfulClientCreationRequestResponseSignatures() throws Exception {
        this.mockMvc.perform(generateCreateRequest(generateCreateParams(false)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        requestFields(CREATE_CLIENT_REQUEST_FIELD_DESCRIPTORS),
                        responseFields(CLIENT_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testSuccessfulEditingRequestResponseSignatures() throws Exception {
        Client testClient = createTestClient(true);
        getEntityManager().persist(testClient);
        this.mockMvc.perform(generateEditRequest(generateEditParams(testClient.getId(), false)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        requestFields(EDIT_CLIENT_REQUEST_FIELD_DESCRIPTORS),
                        responseFields(CLIENT_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testFailedDueToNonExistentEditingRequestResponseSignatures() throws Exception {
        Client testClient = createTestClient(false);
        getEntityManager().persist(testClient);
        this.mockMvc.perform(generateEditRequest(generateEditParams(0, false)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("{method-name}",
                        requestFields(EDIT_CLIENT_REQUEST_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testSuccessfulGettingRequestResponseSignatures() throws Exception {
        Client testClient = createTestClient(true);
        getEntityManager().persist(testClient);
        this.mockMvc.perform(generateGetRequest(testClient.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        pathParameters(parameterWithName("clientId").description("ID of the existing client")),
                        responseFields(CLIENT_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testNonExistentGettingRequestResponseSignatures() throws Exception {
        this.mockMvc.perform(generateGetRequest(0))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("{method-name}",
                        pathParameters(parameterWithName("clientId").description("ID of the existing client"))
                ));
    }

    @Test
    void testSuccessfulAllsGettingRequestResponseSignatures() throws Exception {
        Client testClient = createTestClient(true);
        getEntityManager().persist(testClient);
        this.mockMvc.perform(generateGetAllRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("{method-name}",
                        responseFields(fieldWithPath("[]").description("Array of ClientInfo").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[]", CLIENT_INFO_FIELD_DESCRIPTORS)
                ));
    }

    private static FieldDescriptor[] generateCreateRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(CreateClientRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("name").description("Name of the created client").type(JsonFieldType.STRING),
                docHelper.fieldDescriptorFor("surname").description("Surname of the created client").type(JsonFieldType.STRING),
                docHelper.fieldDescriptorFor("middleName").description("Middle name of the created client").type(JsonFieldType.STRING).optional(),
                docHelper.fieldDescriptorFor("residence").description("Residence of the created client").type(JsonFieldType.STRING),
                docHelper.fieldDescriptorFor("gender")
                        .description("Gender of the created client (false = male, true = female)").type(JsonFieldType.BOOLEAN),
                docHelper.fieldDescriptorFor("job.name").description("Job name of the created client").type(JsonFieldType.STRING),
                docHelper.fieldDescriptorFor("job.organization").description("Job name of the created client").type(JsonFieldType.STRING)
        };
    }

    private static FieldDescriptor[] generateEditClientRequestDescriptors() {
        ConstraintDocumentationHelper docHelper = ConstraintDocumentationHelper.of(EditClientRequest.class);
        return new FieldDescriptor[]{
                docHelper.fieldDescriptorFor("id").description("Id of the edited client").type(JsonFieldType.NUMBER),
                docHelper.fieldDescriptorFor("name").description("Name to update client to").type(JsonFieldType.STRING).optional(),
                docHelper.fieldDescriptorFor("surname").description("Surname to update client to").type(JsonFieldType.STRING).optional(),
                docHelper.fieldDescriptorFor("middleName").description("Middle name to update client to").type(JsonFieldType.STRING).optional(),
                docHelper.fieldDescriptorFor("residence").description("Residence to update client to").type(JsonFieldType.STRING).optional(),
                docHelper.fieldDescriptorFor("gender")
                        .description("Gender to update client to (false = male, true = female)").type(JsonFieldType.BOOLEAN).optional(),
                docHelper.fieldDescriptorFor("job.name").description("Job name to update client to").type(JsonFieldType.STRING).optional(),
                docHelper.fieldDescriptorFor("job.organization")
                        .description("Job organization to update client to").type(JsonFieldType.STRING).optional()
        };
    }
}
