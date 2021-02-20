package com.jupiter.asclepi.core.rest.controller.impl.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.entity.document.Document;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.other.Job;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Getter
@RequiredArgsConstructor
public abstract class AbstractDocumentTest {
    public static final Path TEST_PATH = Paths.get("/testFolder/testFile.testExt");
    public static final String TEST_DESCRIPTION = "testDescription";

    private final ObjectMapper objectMapper;
    private final EntityManager entityManager;

    protected final Map<String, Object> generateCreateParams(boolean withOptional) {
        Map<String, Object> createParams = new HashMap<>();
        createParams.put("path", TEST_PATH);
        if (withOptional) {
            createParams.put("description", TEST_DESCRIPTION);
        }
        return createParams;
    }

    protected final Map<String, Object> generateEditParams(BigInteger id, boolean withOptional) {
        Map<String, Object> params = generateCreateParams(withOptional);
        params.put("id", id);
        return params;
    }

    protected final Document createTestEntity(boolean withOptional) {
        Document document = new Document();
        document.setPath(TEST_PATH);
        if(withOptional){
            document.setDescription(TEST_DESCRIPTION);
        }
        return document;
    }

    protected final MockHttpServletRequestBuilder generateCreateRequest(Map<String, Object> params) throws JsonProcessingException {
        return post("/api/v1/document/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(params));
    }

    protected final MockHttpServletRequestBuilder generateEditRequest(Map<String, Object> params) throws JsonProcessingException {
        return post("/api/v1/document/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(params));
    }

    protected final MockHttpServletRequestBuilder generateGetRequest(BigInteger id) {
        return get("/api/v1/document/{documentId}", id);
    }

    protected final MockHttpServletRequestBuilder generateGetAllRequest() {
        return get("/api/v1/document/all");
    }

    protected final MockHttpServletRequestBuilder generateDeleteRequest(BigInteger id) {
        return delete("/api/v1/document/{documentId}", id);
    }
}
