package com.jupiter.asclepi.core.rest.controller.impl.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.other.Job;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Getter
@RequiredArgsConstructor
public abstract class AbstractClientTest {
    public static final String TEST_NAME = "testName";
    public static final String TEST_SURNAME = "testSurname";
    public static final String TEST_MIDDLE_NAME = "testMiddleName";
    public static final boolean TEST_GENDER = true;
    public static final String TEST_RESIDENCE = "testResidence";
    public static final String TEST_JOB_NAME = "testJobName";
    public static final String TEST_JOB_ORGANIZATION = "testOrganization";

    private final ObjectMapper objectMapper;
    private final EntityManager entityManager;

    protected final Map<String, Object> generateCreateParams(boolean withOptional) {
        Map<String, Object> jobParams = new HashMap<>();
        jobParams.put("name", TEST_JOB_NAME);
        jobParams.put("organization", TEST_JOB_ORGANIZATION);

        Map<String, Object> createParams = new HashMap<>();
        createParams.put("name", TEST_NAME);
        createParams.put("surname", TEST_SURNAME);
        createParams.put("gender", TEST_GENDER);
        if (withOptional) {
            createParams.put("middleName", TEST_MIDDLE_NAME);
        }
        createParams.put("residence", TEST_RESIDENCE);
        createParams.put("job", jobParams);
        return createParams;
    }

    protected final Map<String, Object> generateEditParams(int id, boolean withOptional) {
        Map<String, Object> params = generateCreateParams(withOptional);
        params.put("id", id);
        return params;
    }

    protected final Client createTestClient(boolean withOptional) {
        Client client = new Client();
        client.setName(TEST_NAME);
        client.setSurname(TEST_SURNAME);
        if (withOptional) {
            client.setMiddleName(TEST_MIDDLE_NAME);
        }
        client.setGender(TEST_GENDER);
        client.setResidence(TEST_RESIDENCE);
        Job job = new Job();
        job.setName(TEST_JOB_NAME);
        job.setOrganization(TEST_JOB_ORGANIZATION);
        client.setJob(job);
        return client;
    }

    protected final MockHttpServletRequestBuilder generateCreateRequest(Map<String, Object> params) throws JsonProcessingException {
        return post("/api/v1/client/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(params));
    }

    protected final MockHttpServletRequestBuilder generateEditRequest(Map<String, Object> params) throws JsonProcessingException {
        return post("/api/v1/client/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(params));
    }

    protected final MockHttpServletRequestBuilder generateGetRequest(int id) {
        return get("/api/v1/client/{clientId}", id);
    }

    protected final MockHttpServletRequestBuilder generateGetAllRequest() {
        return get("/api/v1/client/all");
    }

    protected final MockHttpServletRequestBuilder generateDeleteRequest(int id) {
        return delete("/api/v1/client/{clientId}", id);
    }
}
