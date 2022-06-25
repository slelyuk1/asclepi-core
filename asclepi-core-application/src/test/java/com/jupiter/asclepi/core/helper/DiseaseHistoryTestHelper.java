package com.jupiter.asclepi.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.request.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.service.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigInteger;
import java.util.Objects;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
        getter.setClientId(diseaseHistory.getClient().getId());
        getter.setNumber(diseaseHistory.getId().getNumber());
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
        return patch("/api/v1/diseaseHistory/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetRequest(BigInteger clientId, int number) throws JsonProcessingException {
        return get("/api/v1/diseaseHistory/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new GetDiseaseHistoryRequest(clientId, number)));
    }

    public MockHttpServletRequestBuilder createMockedGetAllRequest() {
        return get("/api/v1/diseaseHistory/all");
    }

    public MockHttpServletRequestBuilder createMockedGetForClientRequest(BigInteger clientId) {
        return get("/api/v1/diseaseHistory/{clientId}", clientId);
    }

    public void assertEntityIsValidAfterCreation(CreateDiseaseHistoryRequest request, DiseaseHistory entity) {
        Assertions.assertEquals(request.getClientId(), entity.getClient().getId());
        Assertions.assertEquals(request.getDoctorId(), entity.getDoctor().getId());
        Assertions.assertNotNull(entity.getCreatedWhen());
        String currentLogin = SecurityUtils.getPrincipal(UserDetails.class)
                .map(UserDetails::getUsername)
                .orElse(null);
        Assertions.assertNotNull(currentLogin);
        Assertions.assertEquals(currentLogin, entity.getCreator());
    }

    public void assertEntityIsValidAfterEdition(EditDiseaseHistoryRequest request, DiseaseHistory entity) {
        Assertions.assertEquals(request.getDiseaseHistory().getClientId(), entity.getClient().getId());
        Assertions.assertEquals(request.getDiseaseHistory().getNumber(), entity.getId().getNumber());
        if (Objects.nonNull(request.getDoctorId())) {
            Assertions.assertEquals(request.getDoctorId(), entity.getDoctor().getId());
        }
    }

    @SuppressWarnings("SameParameterValue")
    public void assertEntitiesAreFullyEqual(DiseaseHistory expected, DiseaseHistory actual) {
        Assertions.assertEquals(expected.getClient(), actual.getClient());
        Assertions.assertEquals(expected.getId().getNumber(), actual.getId().getNumber());
        Assertions.assertEquals(expected.getDoctor(), actual.getDoctor());
        Assertions.assertIterableEquals(expected.getDiagnoses(), actual.getDiagnoses());
    }
}
