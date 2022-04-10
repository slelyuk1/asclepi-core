package com.jupiter.asclepi.core.web.controller.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.model.entity.analysis.Analysis;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Objects;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@RequiredArgsConstructor
public class AnalysisTestHelper {
    private static final String TEST_TITLE = "testTitle";
    private static final String TEST_SUMMARY = "testSummary";

    private final ObjectMapper objectMapper;

    public CreateAnalysisRequest generateCreateRequest(Visit visit) {
        CreateAnalysisRequest request = new CreateAnalysisRequest();
        GetVisitRequest visitGetter = new GetVisitRequest(
                new GetDiseaseHistoryRequest(visit.getDiseaseHistory().getClient().getId(), visit.getDiseaseHistory().getNumber()),
                visit.getNumber()
        );
        request.setVisit(visitGetter);
        request.setTitle(TEST_TITLE);
        request.setSummary(TEST_SUMMARY);
        return request;
    }

    public CreateAnalysisRequest generateAnotherCreateRequest(CreateAnalysisRequest request) {
        CreateAnalysisRequest another = request.clone();
        another.setTitle(another.getTitle() + "Other");
        another.setSummary(another.getSummary() + "Other");
        return another;
    }

    public EditAnalysisRequest generateEditRequest(Analysis analysis) {
        DiseaseHistory history = analysis.getVisit().getDiseaseHistory();
        EditAnalysisRequest request = new EditAnalysisRequest();

        GetAnalysisRequest analysisGetter = new GetAnalysisRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getNumber()),
                        analysis.getVisit().getNumber()
                ),
                analysis.getNumber()
        );
        request.setAnalysis(analysisGetter);
        request.setTitle(analysis.getTitle() + "Other");
        request.setSummary(analysis.getSummary() + "Other");
        return request;
    }

    public MockHttpServletRequestBuilder createMockedCreateRequest(CreateAnalysisRequest request) throws JsonProcessingException {
        return post("/api/v1/analysis/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedEditRequest(EditAnalysisRequest request) throws JsonProcessingException {
        return post("/api/v1/analysis/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetRequest(GetAnalysisRequest request) throws JsonProcessingException {
        return get("/api/v1/analysis/get")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedDeleteRequest(GetAnalysisRequest request) throws JsonProcessingException {
        return delete("/api/v1/analysis/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetAllRequest() {
        return get("/api/v1/analysis/all");
    }

    public MockHttpServletRequestBuilder createMockedGetForVisit(GetVisitRequest request) throws JsonProcessingException {
        return get("/api/v1/analysis/getForVisit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetForDiseaseHistory(GetDiseaseHistoryRequest request) throws JsonProcessingException {
        return get("/api/v1/analysis/getForDiseaseHistory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public void assertEntityIsValidAfterCreation(CreateAnalysisRequest request, Analysis entity) {
        GetVisitRequest toCompare = new GetVisitRequest(
                new GetDiseaseHistoryRequest(
                        entity.getVisit().getDiseaseHistory().getClient().getId(),
                        entity.getVisit().getDiseaseHistory().getNumber()
                ),
                entity.getVisit().getNumber()
        );
        Assertions.assertEquals(request.getVisit(), toCompare);
        Assertions.assertEquals(request.getSummary(), entity.getSummary());
        Assertions.assertEquals(request.getTitle(), entity.getTitle());
    }

    public void assertEntityIsValidAfterEdition(EditAnalysisRequest request, Analysis entity) {
        DiseaseHistory history = entity.getVisit().getDiseaseHistory();
        GetAnalysisRequest toCompare = new GetAnalysisRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getNumber()),
                        entity.getVisit().getNumber()
                ),
                entity.getNumber()
        );
        Assertions.assertEquals(request.getAnalysis(), toCompare);
        if(Objects.nonNull(request.getTitle())) {
            Assertions.assertEquals(request.getSummary(), entity.getSummary());
        }
        if(Objects.nonNull(request.getSummary())) {
            Assertions.assertEquals(request.getTitle(), entity.getTitle());
        }
    }

    @SuppressWarnings("SameParameterValue")
    public void assertEntitiesAreFullyEqual(Analysis expected, Analysis actual) {
        Assertions.assertEquals(expected.getVisit(), actual.getVisit());
        Assertions.assertEquals(expected.getNumber(), actual.getNumber());
        Assertions.assertEquals(expected.getTitle(), actual.getTitle());
        Assertions.assertEquals(expected.getSummary(), actual.getSummary());
    }
}
