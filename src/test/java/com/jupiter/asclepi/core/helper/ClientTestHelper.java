package com.jupiter.asclepi.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.other.Job;
import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.people.EditClientRequest;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigInteger;
import java.util.Objects;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@AllArgsConstructor
public class ClientTestHelper {
    private static final String TEST_NAME = "testName";
    private static final String TEST_SURNAME = "testSurname";
    private static final String TEST_MIDDLE_NAME = "testMiddleName";
    private static final boolean TEST_GENDER = true;
    private static final String TEST_RESIDENCE = "testResidence";
    private static final String TEST_JOB_NAME = "testJobName";
    private static final String TEST_JOB_ORGANIZATION = "testOrganization";

    private final ObjectMapper objectMapper;

    public CreateClientRequest generateCreateRequest(boolean withOptional) {
        Job job = new Job();
        job.setName(TEST_JOB_NAME);
        job.setOrganization(TEST_JOB_ORGANIZATION);

        CreateClientRequest request = new CreateClientRequest();
        request.setJob(job);
        request.setName(TEST_NAME);
        request.setSurname(TEST_SURNAME);
        request.setGender(TEST_GENDER);
        if (withOptional) {
            request.setMiddleName(TEST_MIDDLE_NAME);
        }
        request.setResidence(TEST_RESIDENCE);
        return request;
    }

    public CreateClientRequest generateAnotherCreateRequest(CreateClientRequest request) {
        CreateClientRequest another = request.clone();
        another.setName(request.getName() + "Other");
        another.setSurname(another.getSurname() + "Other");
        if (Objects.nonNull(another.getMiddleName())) {
            another.setMiddleName(another.getMiddleName() + "Other");
        }
        another.setGender(!another.getGender());
        another.setResidence(another.getResidence() + "Other");

        Job job = another.getJob();
        job.setName(job.getName() + "Other");
        job.setOrganization(job.getOrganization() + "Other");
        return another;
    }

    public EditClientRequest generateEditRequest(BigInteger id, boolean withOptional) {
        Job job = new Job();
        job.setName(TEST_JOB_NAME);
        job.setOrganization(TEST_JOB_ORGANIZATION);

        EditClientRequest request = new EditClientRequest();
        request.setId(id);
        request.setJob(job);
        request.setName(TEST_NAME);
        request.setSurname(TEST_SURNAME);
        request.setGender(TEST_GENDER);
        if (withOptional) {
            request.setMiddleName(TEST_MIDDLE_NAME);
        }
        request.setResidence(TEST_RESIDENCE);
        return request;
    }

    public MockHttpServletRequestBuilder createMockedCreatedRequest(CreateClientRequest request) throws JsonProcessingException {
        return post("/api/v1/client/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedEditRequest(EditClientRequest request) throws JsonProcessingException {
        return post("/api/v1/client/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetRequest(int id) {
        return get("/api/v1/client/{clientId}", id);
    }

    public MockHttpServletRequestBuilder createMockedGetAllRequest() {
        return get("/api/v1/client/all");
    }

    public void assertEntityIsValidAfterCreation(CreateClientRequest request, Client entity) {
        Assertions.assertEquals(request.getName(), entity.getName());
        Assertions.assertEquals(request.getSurname(), entity.getSurname());
        Assertions.assertEquals(request.getMiddleName(), entity.getMiddleName());
        Assertions.assertEquals(request.getGender(), entity.getGender());
        Assertions.assertEquals(request.getResidence(), entity.getResidence());
        Assertions.assertEquals(request.getJob(), entity.getJob());
    }

    public void assertEntityIsValidAfterEdition(EditClientRequest request, Client entity) {
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
    public void assertEntitiesAreFullyEqual(Client expected, Client actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getSurname(), actual.getSurname());
        Assertions.assertEquals(expected.getMiddleName(), actual.getMiddleName());
        Assertions.assertEquals(expected.getGender(), actual.getGender());
        Assertions.assertEquals(expected.getJob(), actual.getJob());
        Assertions.assertEquals(expected.getResidence(), actual.getResidence());
    }
}
