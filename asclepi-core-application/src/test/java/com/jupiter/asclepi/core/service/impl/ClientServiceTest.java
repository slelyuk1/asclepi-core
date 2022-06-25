package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.configuration.TestHelperConfiguration;
import com.jupiter.asclepi.core.helper.ClientTestHelper;
import com.jupiter.asclepi.core.model.request.client.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.client.EditClientRequest;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.service.api.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Objects;

@Transactional
@SpringBootTest
@Import(TestHelperConfiguration.class)
class ClientServiceTest {

    private final ClientTestHelper helper;
    private final ClientService service;
    private final EntityManager entityManager;

    @Autowired
    public ClientServiceTest(ClientTestHelper helper, ClientService service, EntityManager entityManager) {
        this.helper = helper;
        this.service = service;
        this.entityManager = entityManager;
    }

    @Test
    @WithMockUser
    void testSuccessfulCreation() {
        CreateClientRequest request = helper.generateCreateRequest(false);
        Client created = service.create(request);
        entityManager.flush();
        entityManager.detach(created);
        helper.assertEntityIsValidAfterCreation(request, created);
    }

    @Test
    void testSuccessfulEditing() {
        CreateClientRequest createRequest = helper.generateCreateRequest(false);
        Client created = service.create(createRequest);
        entityManager.flush();
        entityManager.detach(created);

        EditClientRequest editRequest = helper.generateEditRequest(created.getId(), true);
        Client edited = service.edit(editRequest);
        helper.assertEntityIsValidAfterEdition(editRequest, edited);
    }

    @Test
    void testSuccessfulGetting() {
        CreateClientRequest createRequest = helper.generateCreateRequest(false);
        Client created = service.create(createRequest);
        entityManager.flush();
        entityManager.detach(created);

        Client found = service.getOne(created.getId())
                .orElseThrow(() -> new IllegalStateException("Created entity cannot be found!"));
        helper.assertEntitiesAreFullyEqual(created, found);
    }

    @Test
    void testSuccessfulGettingAll() {
        CreateClientRequest createRequest = helper.generateCreateRequest(false);
        Client one = service.create(createRequest);
        Client another = service.create(helper.generateAnotherCreateRequest(createRequest));
        entityManager.flush();
        entityManager.detach(one);
        entityManager.detach(another);

        Collection<Client> all = service.getAll();
        Assertions.assertEquals(2, all.size());
        Client oneInfo = all.stream()
                .filter(info -> Objects.equals(info.getId(), one.getId()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        Client anotherInfo = all.stream()
                .filter(info -> Objects.equals(info.getId(), another.getId()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("List doesn't contain persisted element!"));
        helper.assertEntitiesAreFullyEqual(one, oneInfo);
        helper.assertEntitiesAreFullyEqual(another, anotherInfo);
    }
}
