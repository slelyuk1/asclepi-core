package com.jupiter.asclepi.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.repository.helper.api.CreationData;
import com.jupiter.asclepi.core.service.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RequiredArgsConstructor
public class VisitTestHelper {
    private static final LocalDateTime TEST_WHEN = LocalDateTime.now().plusDays(1).withNano(0);

    private final DiseaseHistoryTestHelper diseaseHistoryTestHelper;
    private final ObjectMapper objectMapper;

    public CreateVisitRequest generateCreateRequest(DiseaseHistory history) {
        CreateVisitRequest request = new CreateVisitRequest();
        GetDiseaseHistoryRequest getter = new GetDiseaseHistoryRequest();
        getter.setClientId(history.getClient().getId());
        getter.setNumber(history.getId().getNumber());
        request.setDiseaseHistory(getter);
        request.setWhen(TEST_WHEN);
        return request;
    }

    public CreateVisitRequest generateAnotherCreateRequest(CreateVisitRequest request) {
        CreateVisitRequest another = new CreateVisitRequest();
        BeanUtils.copyProperties(request, another);
        request.setWhen(request.getWhen().plusDays(1));
        return another;
    }

    public EditVisitRequest generateEditRequest(Visit visit, LocalDateTime when) {
        EditVisitRequest request = new EditVisitRequest();
        GetVisitRequest getter = new GetVisitRequest();
        GetDiseaseHistoryRequest diseaseHistoryGetter = new GetDiseaseHistoryRequest();
        diseaseHistoryGetter.setClientId(visit.getDiseaseHistory().getClient().getId());
        diseaseHistoryGetter.setNumber(visit.getDiseaseHistory().getId().getNumber());
        getter.setDiseaseHistory(diseaseHistoryGetter);
        getter.setNumber(visit.getId().getNumber());
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
        return patch("/api/v1/visit/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetRequest(GetVisitRequest request) throws JsonProcessingException {
        return get("/api/v1/visit/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetAllRequest() {
        return get("/api/v1/visit/all");
    }

    public MockHttpServletRequestBuilder createMockedGetForDiseaseHistory(GetDiseaseHistoryRequest request) {
        return get("/api/v1/visit/{clientId}/{diseaseHistoryNumber}/", request.getClientId(), request.getNumber());
    }

    public void assertEntityIsValidAfterCreation(CreateVisitRequest request, Visit entity) {
        Assertions.assertEquals(request.getDiseaseHistory().getClientId(), entity.getDiseaseHistory().getClient().getId());
        Assertions.assertEquals(request.getDiseaseHistory().getNumber(), entity.getDiseaseHistory().getId().getNumber());
        Assertions.assertEquals(request.getWhen(), entity.getWhen());
        CreationData<String> creation = entity.getCreation();
        Assertions.assertNotNull(creation);
        Assertions.assertNotNull(creation.getWhen());
        String currentLogin = SecurityUtils.getPrincipal(UserDetails.class)
                .map(UserDetails::getUsername)
                .orElse(null);
        Assertions.assertNotNull(currentLogin);
        Assertions.assertEquals(currentLogin, creation.getBy());
    }

    public void assertEntityIsValidAfterEdition(EditVisitRequest request, Visit entity) {
        Assertions.assertEquals(request.getVisit().getDiseaseHistory().getClientId(), entity.getDiseaseHistory().getClient().getId());
        Assertions.assertEquals(request.getVisit().getDiseaseHistory().getNumber(), entity.getDiseaseHistory().getId().getNumber());
        Assertions.assertEquals(request.getVisit().getNumber(), entity.getId().getNumber());
        if (Objects.nonNull(request.getWhen())) {
            Assertions.assertEquals(request.getWhen(), entity.getWhen());
        }
    }

    @SuppressWarnings("SameParameterValue")
    public void assertEntitiesAreFullyEqual(Visit expected, Visit actual) {
        Assertions.assertEquals(expected.getDiseaseHistory(), actual.getDiseaseHistory());
        Assertions.assertEquals(expected.getId().getNumber(), actual.getId().getNumber());
        Assertions.assertEquals(expected.getWhen(), actual.getWhen());
    }
}
