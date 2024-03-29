package com.jupiter.asclepi.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@RequiredArgsConstructor
public class VisitTestHelper {
    private static final LocalDateTime TEST_WHEN = LocalDateTime.now().plusDays(1);

    private final DiseaseHistoryTestHelper diseaseHistoryTestHelper;
    private final ObjectMapper objectMapper;

    public CreateVisitRequest generateCreateRequest(DiseaseHistory history) {
        CreateVisitRequest request = new CreateVisitRequest();
        GetDiseaseHistoryRequest getter = new GetDiseaseHistoryRequest();
        getter.setClientId(history.getClient().getId());
        getter.setNumber(history.getNumber());
        request.setDiseaseHistory(getter);
        request.setWhen(TEST_WHEN);
        return request;
    }

    public CreateVisitRequest generateAnotherCreateRequest(CreateVisitRequest request) {
        CreateVisitRequest another = request.clone();
        request.setWhen(request.getWhen().plusDays(1));
        return another;
    }

    public EditVisitRequest generateEditRequest(Visit visit, LocalDateTime when) {
        EditVisitRequest request = new EditVisitRequest();
        GetVisitRequest getter = new GetVisitRequest();
        GetDiseaseHistoryRequest diseaseHistoryGetter = new GetDiseaseHistoryRequest();
        diseaseHistoryGetter.setClientId(visit.getDiseaseHistory().getClient().getId());
        diseaseHistoryGetter.setNumber(visit.getDiseaseHistory().getNumber());
        getter.setDiseaseHistory(diseaseHistoryGetter);
        getter.setNumber(visit.getNumber());
        request.setVisit(getter);
        request.setWhen(when);
        return request;
    }

    public MockHttpServletRequestBuilder createMockedCreateRequest(CreateVisitRequest request) throws JsonProcessingException {
        return post("/api/v1/visit/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedEditRequest(EditVisitRequest request) throws JsonProcessingException {
        return post("/api/v1/visit/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetRequest(GetVisitRequest request) {
        return get("/api/v1/visit/{clientId}/{diseaseHistoryNumber}/{number}/",
                request.getDiseaseHistory().getClientId(),
                request.getDiseaseHistory().getNumber(),
                request.getNumber());
    }

    public MockHttpServletRequestBuilder createMockedGetAllRequest() {
        return get("/api/v1/visit/all");
    }

    public MockHttpServletRequestBuilder createMockedGetForDiseaseHistory(GetDiseaseHistoryRequest request) {
        return get("/api/v1/visit/{clientId}/{diseaseHistoryNumber}/", request.getClientId(), request.getNumber());
    }

    public void assertEntityIsValidAfterCreation(CreateVisitRequest request, Visit entity) {
        Assertions.assertEquals(request.getDiseaseHistory().getClientId(), entity.getDiseaseHistory().getClient().getId());
        Assertions.assertEquals(request.getDiseaseHistory().getNumber(), entity.getDiseaseHistory().getNumber());
        Assertions.assertEquals(request.getWhen(), entity.getWhen());
    }

    public void assertEntityIsValidAfterEdition(EditVisitRequest request, Visit entity) {
        Assertions.assertEquals(request.getVisit().getDiseaseHistory().getClientId(), entity.getDiseaseHistory().getClient().getId());
        Assertions.assertEquals(request.getVisit().getDiseaseHistory().getNumber(), entity.getDiseaseHistory().getNumber());
        Assertions.assertEquals(request.getVisit().getNumber(), entity.getNumber());
        if (Objects.nonNull(request.getWhen())) {
            Assertions.assertEquals(request.getWhen(), entity.getWhen());
        }
    }

    @SuppressWarnings("SameParameterValue")
    public void assertEntitiesAreFullyEqual(Visit expected, Visit actual) {
        Assertions.assertEquals(expected.getDiseaseHistory(), actual.getDiseaseHistory());
        Assertions.assertEquals(expected.getNumber(), actual.getNumber());
        Assertions.assertEquals(expected.getWhen(), actual.getWhen());
    }
}
