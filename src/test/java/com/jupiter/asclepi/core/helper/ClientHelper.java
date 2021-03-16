package com.jupiter.asclepi.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.other.Job;
import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.people.EditClientRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Objects;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@AllArgsConstructor
public class ClientHelper {
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

    public EditClientRequest generateEditRequest(int id, boolean withOptional) {
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

    public MockHttpServletRequestBuilder createMockedDeleteRequest(int id) {
        return delete("/api/v1/client/{clientId}", id);
    }
}
