package com.jupiter.asclepi.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.EditAnamnesisRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigInteger;
import java.util.Objects;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@RequiredArgsConstructor
public class AnamnesisTestHelper {
    private static final String TEST_COMPLAINTS = "testComplaints";
    private static final String TEST_MORBI = "testMorbi";
    private static final String TEST_VITAE = "testVitae";

    private final ObjectMapper objectMapper;

    public CreateAnamnesisRequest generateCreateRequest(DiseaseHistory diseaseHistory) {
        CreateAnamnesisRequest request = new CreateAnamnesisRequest();
        GetDiseaseHistoryRequest getter = new GetDiseaseHistoryRequest(diseaseHistory.getClient().getId(), diseaseHistory.getNumber());
        request.setDiseaseHistory(getter);
        request.setComplaints(TEST_COMPLAINTS);
        request.setMorbi(TEST_MORBI);
        request.setVitae(TEST_VITAE);
        return request;
    }

    public CreateAnamnesisRequest generateAnotherCreateRequest(CreateAnamnesisRequest request) {
        CreateAnamnesisRequest another = request.clone();
        another.setComplaints(another.getComplaints() + "Other");
        another.setMorbi(another.getMorbi() + "Other");
        another.setVitae(another.getVitae() + "Other");
        return another;
    }

    public EditAnamnesisRequest generateEditRequest(Anamnesis anamnesis) {
        EditAnamnesisRequest request = new EditAnamnesisRequest();
        request.setId(anamnesis.getId());
        request.setComplaints(TEST_COMPLAINTS + "Others");
        request.setMorbi(TEST_MORBI + "Others");
        request.setVitae(TEST_VITAE + "Others");
        return request;
    }

    public MockHttpServletRequestBuilder createMockedCreateRequest(CreateAnamnesisRequest request) throws JsonProcessingException {
        return post("/api/v1/anamnesis/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetRequest(BigInteger id) throws JsonProcessingException {
        return get("/api/v1/anamnesis/{anamnesisId}", id);
    }

    public MockHttpServletRequestBuilder createMockedDeleteRequest(BigInteger id) throws JsonProcessingException {
        return delete("/api/v1/anamnesis/{anamnesisId}/delete", id);
    }

    public MockHttpServletRequestBuilder createMockedGetAllRequest() {
        return get("/api/v1/anamnesis/all");
    }

    public MockHttpServletRequestBuilder createMockedGetForDiseaseHistory(GetDiseaseHistoryRequest request) throws JsonProcessingException {
        return get("/api/v1/anamnesis/getForDiseaseHistory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public void assertEntityIsValidAfterCreation(CreateAnamnesisRequest request, Anamnesis entity) {
        DiseaseHistory history = entity.getDiseaseHistory();
        GetDiseaseHistoryRequest toCompare = new GetDiseaseHistoryRequest(history.getClient().getId(), history.getNumber());
        Assertions.assertEquals(request.getDiseaseHistory(), toCompare);
        Assertions.assertEquals(request.getComplaints(), entity.getComplaints());
        Assertions.assertEquals(request.getMorbi(), entity.getMorbi());
        Assertions.assertEquals(request.getVitae(), entity.getVitae());
    }

    public void assertEntityIsValidAfterEdition(EditAnamnesisRequest request, Anamnesis entity) {
        Assertions.assertEquals(request.getId(), entity.getId());
        if (Objects.nonNull(request.getVitae())) {
            Assertions.assertEquals(request.getVitae(), entity.getVitae());
        }
        if (Objects.nonNull(request.getMorbi())) {
            Assertions.assertEquals(request.getMorbi(), entity.getMorbi());
        }
        if (Objects.nonNull(request.getComplaints())) {
            Assertions.assertEquals(request.getComplaints(), entity.getComplaints());
        }
    }

    @SuppressWarnings("SameParameterValue")
    public void assertEntitiesAreFullyEqual(Anamnesis expected, Anamnesis actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getDiseaseHistory(), actual.getDiseaseHistory());
        Assertions.assertEquals(expected.getComplaints(), actual.getComplaints());
        Assertions.assertEquals(expected.getVitae(), actual.getVitae());
        Assertions.assertEquals(expected.getMorbi(), actual.getMorbi());
    }
}
