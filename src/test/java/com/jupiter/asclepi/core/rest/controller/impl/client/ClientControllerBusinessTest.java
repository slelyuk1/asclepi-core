package com.jupiter.asclepi.core.rest.controller.impl.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.response.people.ClientInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Objects;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
public class ClientControllerBusinessTest extends AbstractClientTest {

    private MockMvc mockMvc;

    @Autowired
    public ClientControllerBusinessTest(ObjectMapper objectMapper, EntityManager entityManager) {
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
                    ClientInfo info = getObjectMapper().readValue(result.getResponse().getContentAsString(), ClientInfo.class);
                    Client created = getEntityManager().find(Client.class, info.getId());
                    assertClientIsEqualToClientInfo(created, info);
                });
    }

    @Test
    void testSuccessfulEditing() throws Exception {
        Client toEdit = createAnother(true);
        getEntityManager().persist(toEdit);

        this.mockMvc.perform(generateEditRequest(generateEditParams(toEdit.getId(), true)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    ClientInfo info = getObjectMapper().readValue(result.getResponse().getContentAsString(), ClientInfo.class);
                    assertClientIsEqualToClientInfo(toEdit, info);
                });
    }

    @Test
    void testSuccessfulGetting() throws Exception {
        Client test = createTestClient(false);
        getEntityManager().persist(test);
        this.mockMvc.perform(generateGetRequest(test.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    ClientInfo info = getObjectMapper().readValue(result.getResponse().getContentAsString(), ClientInfo.class);
                    assertClientIsEqualToClientInfo(test, info);
                });
    }

    @Test
    void testSuccessfulGettingAll() throws Exception {
        Client one = createTestClient(true);
        Client another = createAnother(true);
        getEntityManager().persist(one);
        getEntityManager().persist(another);
        this.mockMvc.perform(generateGetAllRequest())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    ClientInfo[] infos = getObjectMapper().readValue(result.getResponse().getContentAsString(), ClientInfo[].class);
                    Assertions.assertEquals(infos.length, 2);
                    ClientInfo oneInfo = Arrays.stream(infos)
                            .filter(info -> Objects.equals(info.getId(), one.getId()))
                            .findAny()
                            .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
                    ClientInfo anotherInfo = Arrays.stream(infos)
                            .filter(info -> Objects.equals(info.getId(), another.getId()))
                            .findAny()
                            .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
                    assertClientIsEqualToClientInfo(one, oneInfo);
                    assertClientIsEqualToClientInfo(another, anotherInfo);
                });
    }

    @Test
    void testSuccessfulDeletion() throws Exception {
        Client test = createTestClient(true);
        getEntityManager().persist(test);
        this.mockMvc.perform(generateDeleteRequest(test.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(result -> {
                    Client found = getEntityManager().find(Client.class, test.getId());
                    Assertions.assertNull(found);
                });
    }

    @SuppressWarnings("SameParameterValue")
    private Client createAnother(boolean withOptional) {
        Client other = createTestClient(true);
        other.setName(other.getName() + "Other");
        other.setSurname(other.getSurname() + "Other");
        if (withOptional) {
            other.setMiddleName(other.getMiddleName() + "Other");
        }
        other.setGender(!other.getGender());
        other.setResidence(other.getResidence() + "Other");
        other.getJob().setName(other.getJob().getName() + "Other");
        other.getJob().setOrganization(other.getJob().getOrganization() + "Other");
        return other;
    }

    private void assertClientIsEqualToClientInfo(Client client, ClientInfo clientInfo) {
        Assertions.assertEquals(client.getId(), clientInfo.getId());
        Assertions.assertEquals(client.getName(), clientInfo.getName());
        Assertions.assertEquals(client.getSurname(), clientInfo.getSurname());
        Assertions.assertEquals(client.getMiddleName(), clientInfo.getMiddleName());
        Assertions.assertEquals(client.getGender(), clientInfo.getGender());
        Assertions.assertEquals(client.getJob(), clientInfo.getJob());
        Assertions.assertEquals(client.getResidence(), clientInfo.getResidence());
    }
}
