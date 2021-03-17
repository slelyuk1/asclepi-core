package com.jupiter.asclepi.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.request.disease.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.EditVisitRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigInteger;
import java.util.Objects;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@RequiredArgsConstructor
public class DiseaseHistoryTestHelper {
    private final ObjectMapper objectMapper;

    public CreateDiseaseHistoryRequest generateCreateRequest(BigInteger clientId, Integer doctorId) {
        CreateDiseaseHistoryRequest request = new CreateDiseaseHistoryRequest();
        request.setClientId(clientId);
        request.setDoctorId(doctorId);
        return request;
    }

    public EditDiseaseHistoryRequest generateEditRequest(DiseaseHistory diseaseHistory, Integer doctorId) {
        EditDiseaseHistoryRequest request = new EditDiseaseHistoryRequest();
        GetDiseaseHistoryRequest getter = new GetDiseaseHistoryRequest();
        getter.setClientId(diseaseHistory.getClientId());
        getter.setNumber(diseaseHistory.getNumber());
        request.setDoctorId(doctorId);
        request.setDiseaseHistory(getter);
        return request;
    }

    public MockHttpServletRequestBuilder createMockedCreatedRequest(CreateDiseaseHistoryRequest request) throws JsonProcessingException {
        return post("/api/v1/diseaseHistory/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedEditRequest(EditDiseaseHistoryRequest request) throws JsonProcessingException {
        return post("/api/v1/diseaseHistory/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetRequest(BigInteger clientId, int number) {
        return get("/api/v1/diseaseHistory/{clientId}/{number}", clientId, number);
    }

    public MockHttpServletRequestBuilder createMockedGetAllRequest() {
        return get("/api/v1/diseaseHistory/all");
    }

    public MockHttpServletRequestBuilder createMockedGetForClientRequest(BigInteger clientId) {
        return get("/api/v1/diseaseHistory/{clientId}", clientId);
    }

    public MockHttpServletRequestBuilder createMockedAbortRequest(BigInteger clientId, int historyNumber) {
        return post("/api/v1/diseaseHistory/{clientId}/{historyNumber}/abort", clientId, historyNumber);
    }

    public MockHttpServletRequestBuilder createMockedCloseRequest(BigInteger clientId, int historyNumber) {
        return post("/api/v1/diseaseHistory/{clientId}/{historyNumber}/close", clientId, historyNumber);
    }

    public void assertEntityIsValidAfterCreation(CreateDiseaseHistoryRequest request, DiseaseHistory entity) {
        Assertions.assertEquals(request.getClientId(), entity.getClientId());
        Assertions.assertEquals(request.getDoctorId(), entity.getDoctor().getId());
    }

    public void assertEntityIsValidAfterEdition(EditDiseaseHistoryRequest request, DiseaseHistory entity) {
        Assertions.assertEquals(request.getDiseaseHistory().getClientId(), entity.getClientId());
        Assertions.assertEquals(request.getDiseaseHistory().getNumber(), entity.getNumber());
        if (Objects.nonNull(request.getDoctorId())) {
            Assertions.assertEquals(request.getDoctorId(), entity.getDoctor().getId());
        }
    }

    @SuppressWarnings("SameParameterValue")
    public void assertEntitiesAreFullyEqual(DiseaseHistory expected, DiseaseHistory actual) {
        Assertions.assertEquals(expected.getClientId(), actual.getClientId());
        Assertions.assertEquals(expected.getNumber(), actual.getNumber());
        Assertions.assertEquals(expected.getDoctor(), actual.getDoctor());
        Assertions.assertIterableEquals(expected.getDiagnoses(), actual.getDiagnoses());
    }
}
