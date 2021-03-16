package com.jupiter.asclepi.core.rest.controller.impl.client;

import com.jupiter.asclepi.core.helper.ClientTestHelper;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.people.EditClientRequest;
import com.jupiter.asclepi.core.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Objects;

@Transactional
@SpringBootTest
public class ClientControllerBusinessTest {

    private MockMvc mockMvc;
    private final ClientTestHelper helper;
    private final ClientService service;
    private final EntityManager entityManager;

    @Autowired
    public ClientControllerBusinessTest(ClientTestHelper helper, ClientService service, EntityManager entityManager) {
        this.helper = helper;
        this.service = service;
        this.entityManager = entityManager;
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testSuccessfulCreation() {
        CreateClientRequest request = helper.generateCreateRequest(false);
        Client created = service.create(request).get();
        entityManager.flush();
        entityManager.detach(created);
        assertEntityIsValidAfterCreation(request, created);
    }

    @Test
    void testSuccessfulEditing() {
        CreateClientRequest createRequest = helper.generateCreateRequest(false);
        Client created = service.create(createRequest).get();
        entityManager.flush();
        entityManager.detach(created);

        EditClientRequest editRequest = helper.generateEditRequest(created.getId(), true);
        Client edited = service.edit(editRequest).get();
        assertEntityIsValidAfterEdition(editRequest, edited);
    }

    @Test
    void testSuccessfulGetting() {
        CreateClientRequest createRequest = helper.generateCreateRequest(false);
        Client created = service.create(createRequest).get();
        entityManager.flush();
        entityManager.detach(created);

        Client found = service.getOne(created.getId())
                .orElseThrow(() -> new IllegalStateException("Created entity cannot be found!"));
        assertEntitiesAreFullyEqual(created, found);
    }

    @Test
    void testSuccessfulGettingAll() throws Exception {
        CreateClientRequest createRequest = helper.generateCreateRequest(false);
        Client one = service.create(createRequest).get();
        Client another = service.create(helper.generateAnotherCreateRequest(createRequest)).get();
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Client> all = service.getAll();
        Assertions.assertEquals(all.size(), 2);
        Client oneInfo = all.stream()
                .filter(info -> Objects.equals(info.getId(), one.getId()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Client anotherInfo = all.stream()
                .filter(info -> Objects.equals(info.getId(), another.getId()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        assertEntitiesAreFullyEqual(one, oneInfo);
        assertEntitiesAreFullyEqual(another, anotherInfo);
    }

    private void assertEntityIsValidAfterCreation(CreateClientRequest request, Client entity) {
        Assertions.assertEquals(request.getName(), entity.getName());
        Assertions.assertEquals(request.getSurname(), entity.getSurname());
        Assertions.assertEquals(request.getMiddleName(), entity.getMiddleName());
        Assertions.assertEquals(request.getGender(), entity.getGender());
        Assertions.assertEquals(request.getResidence(), entity.getResidence());
        Assertions.assertEquals(request.getJob(), entity.getJob());
    }

    private void assertEntityIsValidAfterEdition(EditClientRequest request, Client entity) {
        Assertions.assertEquals(request.getId(), entity.getId());
        if (Objects.nonNull(request.getName())) {
            Assertions.assertEquals(request.getName(), entity.getName());
        }
        if (Objects.nonNull(request.getSurname())) {
            Assertions.assertEquals(request.getSurname(), entity.getSurname());
        }
        if (Objects.nonNull(request.getMiddleName())) {
            Assertions.assertEquals(request.getMiddleName(), entity.getMiddleName());
        }
        if (Objects.nonNull(request.getGender())) {
            Assertions.assertEquals(request.getGender(), entity.getGender());
        }
        if (Objects.nonNull(request.getResidence())) {
            Assertions.assertEquals(request.getResidence(), entity.getResidence());
        }
        if (Objects.nonNull(request.getJob())) {
            Assertions.assertEquals(request.getJob(), entity.getJob());
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void assertEntitiesAreFullyEqual(Client expected, Client actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getSurname(), actual.getSurname());
        Assertions.assertEquals(expected.getMiddleName(), actual.getMiddleName());
        Assertions.assertEquals(expected.getGender(), actual.getGender());
        Assertions.assertEquals(expected.getJob(), actual.getJob());
        Assertions.assertEquals(expected.getResidence(), actual.getResidence());
    }
}
