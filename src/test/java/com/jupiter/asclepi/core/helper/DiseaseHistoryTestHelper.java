package com.jupiter.asclepi.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.entity.disease.DiseaseHistory;
import com.jupiter.asclepi.core.model.request.disease.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.history.EditDiseaseHistoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigInteger;

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

    public EditDiseaseHistoryRequest generateEditRequest(DiseaseHistory entity, Integer doctorId) {
        EditDiseaseHistoryRequest request = new EditDiseaseHistoryRequest();
        request.setDoctorId(entity.getDoctor().getId());
        request.setNumber(entity.getNumber());
        request.setDoctorId(doctorId);
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

    public MockHttpServletRequestBuilder createMockedGetRequest(BigInteger clientId, int historyNumber) {
        return get("/api/v1/diseaseHistory/{clientId}/{historyNumber}", clientId, historyNumber);
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
}
