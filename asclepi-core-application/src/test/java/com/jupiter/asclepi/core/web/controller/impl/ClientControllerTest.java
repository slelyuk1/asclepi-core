package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.ClientTestHelper;
import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.people.EditClientRequest;
import com.jupiter.asclepi.core.repository.entity.Client;
import com.jupiter.asclepi.core.service.api.ClientService;
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
class ClientControllerTest {

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

    private final ClientTestHelper helper;
    private final ClientService service;
    private MockMvc mockMvc;

    @Autowired
    public ClientControllerTest(ClientTestHelper helper, ClientService service) {
        this.helper = helper;
        this.service = service;
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

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = TestUtils.createMockMvcDefaultConfiguration(webApplicationContext, restDocumentation);
    }

    @Test
    void testSuccessfulClientCreationRequestResponseSignatures() throws Exception {
        this.mockMvc.perform(helper.createMockedCreatedRequest(helper.generateCreateRequest(false)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("clientSuccessfulCreation",
                        requestFields(CREATE_CLIENT_REQUEST_FIELD_DESCRIPTORS),
                        responseFields(CLIENT_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testSuccessfulEditingRequestResponseSignatures() throws Exception {
        CreateClientRequest request = helper.generateCreateRequest(false);
        Client created = service.create(request).get();
        this.mockMvc.perform(helper.createMockedEditRequest(helper.generateEditRequest(created.getId(), false)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("clientSuccessfulEdition",
                        requestFields(EDIT_CLIENT_REQUEST_FIELD_DESCRIPTORS),
                        responseFields(CLIENT_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testFailedDueToNonExistentEditingRequestResponseSignatures() throws Exception {
        this.mockMvc.perform(helper.createMockedEditRequest(helper.generateEditRequest(BigInteger.ZERO, false)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("clientNonExistentEdition",
                        requestFields(EDIT_CLIENT_REQUEST_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testSuccessfulGettingRequestResponseSignatures() throws Exception {
        CreateClientRequest request = helper.generateCreateRequest(false);
        Client created = service.create(request).get();
        this.mockMvc.perform(helper.createMockedGetRequest(created.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("clientSuccessfulGetting",
                        pathParameters(parameterWithName("clientId").description("ID of the existing client")),
                        responseFields(CLIENT_INFO_FIELD_DESCRIPTORS)
                ));
    }

    @Test
    void testNonExistentGettingRequestResponseSignatures() throws Exception {
        this.mockMvc.perform(helper.createMockedGetRequest(BigInteger.ZERO))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("clientNonExistentGetting",
                        pathParameters(parameterWithName("clientId").description("ID of the existing client"))
                ));
    }

    @Test
    void testSuccessfulAllGettingRequestResponseSignatures() throws Exception {
        CreateClientRequest request = helper.generateCreateRequest(false);
        service.create(request).get();
        this.mockMvc.perform(helper.createMockedGetAllRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("clientSuccessfulGettingAll",
                        responseFields(fieldWithPath("[]").description("Array of ClientInfo").type(JsonFieldType.ARRAY))
                                .andWithPrefix("[]", CLIENT_INFO_FIELD_DESCRIPTORS)
                ));
    }
}
