package com.jupiter.asclepi.core.helper.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.request.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.repository.entity.Document;
import com.jupiter.asclepi.core.repository.entity.analysis.Analysis;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Getter
@RequiredArgsConstructor
public abstract class AbstractDocumentTest {
    public static final Path TEST_PATH = Paths.get("/testFolder/testFile.testExt");
    public static final String TEST_DESCRIPTION = "testDescription";

    private final ObjectMapper objectMapper;
    private final EntityManager entityManager;

    protected final Map<String, Object> generateCreateParams(Analysis analysis, boolean withOptional) {
        Map<String, Object> createParams = new HashMap<>();
        createParams.put("path", TEST_PATH);
        if (withOptional) {
            createParams.put("description", TEST_DESCRIPTION);
        }
        createParams.put("analysis", new GetAnalysisRequest(
                        new GetVisitRequest(
                                new GetDiseaseHistoryRequest(
                                        analysis.getId().getVisitId().getDiseaseHistoryId().getClientId(),
                                        analysis.getId().getVisitId().getDiseaseHistoryId().getNumber()
                                ),
                                analysis.getId().getVisitId().getNumber()
                        ),
                        analysis.getId().getNumber()
                )
        );
        return createParams;
    }

    protected final Map<String, Object> generateEditParams(BigInteger id, boolean withOptional) {
        Map<String, Object> params = new HashMap<>();
        if (withOptional) {
            params.put("description", TEST_DESCRIPTION);
        }
        params.put("id", id);
        return params;
    }

    protected final Document createTestEntity(Analysis analysis, boolean withOptional) {
        Document document = new Document();
        document.setPath(TEST_PATH);
        if (withOptional) {
            document.setDescription(TEST_DESCRIPTION);
        }
        document.setAnalysis(analysis);
        return document;
    }

    protected final MockHttpServletRequestBuilder generateCreateRequest(Map<String, Object> params) throws JsonProcessingException {
        return post("/api/v1/document/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(params));
    }

    protected final MockHttpServletRequestBuilder generateEditRequest(Map<String, Object> params) throws JsonProcessingException {
        return patch("/api/v1/document/")
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
