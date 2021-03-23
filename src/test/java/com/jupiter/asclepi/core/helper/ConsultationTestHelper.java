package com.jupiter.asclepi.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.entity.disease.consultation.Consultation;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.request.disease.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
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
public class ConsultationTestHelper {
    private static final String TEST_INSPECTION = "testInspection";

    private final ObjectMapper objectMapper;

    public CreateConsultationRequest generateCreateRequest(Visit visit, Anamnesis anamnesis) {
        CreateConsultationRequest request = new CreateConsultationRequest();
        GetVisitRequest visitGetter = new GetVisitRequest(
                new GetDiseaseHistoryRequest(visit.getDiseaseHistory().getClient().getId(), visit.getDiseaseHistory().getNumber()),
                visit.getNumber()
        );
        request.setVisit(visitGetter);
        request.setAnamnesisId(anamnesis.getId());
        request.setInspection(TEST_INSPECTION);
        return request;
    }

    public CreateConsultationRequest generateAnotherCreateRequest(CreateConsultationRequest request) {
        CreateConsultationRequest another = request.clone();
        another.setInspection(another.getInspection() + "Other");
        return another;
    }

    public EditConsultationRequest generateEditRequest(Consultation consultation) {
        DiseaseHistory history = consultation.getVisit().getDiseaseHistory();
        EditConsultationRequest request = new EditConsultationRequest();
        GetConsultationRequest analysisGetter = new GetConsultationRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getNumber()),
                        consultation.getVisit().getNumber()
                ),
                consultation.getNumber()
        );
        request.setInspection(consultation.getInspection() + "Other");
        return request;
    }

    public MockHttpServletRequestBuilder createMockedCreateRequest(CreateConsultationRequest request) throws JsonProcessingException {
        return post("/api/v1/consultation/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedEditRequest(EditConsultationRequest request) throws JsonProcessingException {
        return post("/api/v1/consultation/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetRequest(GetConsultationRequest request) throws JsonProcessingException {
        return get("/api/v1/consultation/get")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedDeleteRequest(GetConsultationRequest request) throws JsonProcessingException {
        return delete("/api/v1/consultation/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetAllRequest() {
        return get("/api/v1/consultation/all");
    }

    public MockHttpServletRequestBuilder createMockedGetForVisit(GetVisitRequest request) throws JsonProcessingException {
        return get("/api/v1/consultation/getForVisit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetForDiseaseHistory(GetDiseaseHistoryRequest request) throws JsonProcessingException {
        return get("/api/v1/consultation/getForDiseaseHistory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public void assertEntityIsValidAfterCreation(CreateConsultationRequest request, Consultation entity) {
        GetVisitRequest toCompare = new GetVisitRequest(
                new GetDiseaseHistoryRequest(
                        entity.getVisit().getDiseaseHistory().getClient().getId(),
                        entity.getVisit().getDiseaseHistory().getNumber()
                ),
                entity.getVisit().getNumber()
        );
        Assertions.assertEquals(request.getVisit(), toCompare);
        Assertions.assertEquals(request.getAnamnesisId(), entity.getAnamnesis().getId());
        Assertions.assertEquals(request.getInspection(), entity.getInspection());
    }

    public void assertEntityIsValidAfterEdition(EditConsultationRequest request, Consultation entity) {
        DiseaseHistory history = entity.getVisit().getDiseaseHistory();
        GetConsultationRequest toCompare = new GetConsultationRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getNumber()),
                        entity.getVisit().getNumber()
                ),
                entity.getNumber()
        );
        Assertions.assertEquals(request.getConsultation(), toCompare);
        if (Objects.nonNull(request.getAnamnesisId())) {
            Assertions.assertEquals(request.getAnamnesisId(), entity.getAnamnesis().getId());
        }
        if (Objects.nonNull(request.getInspection())) {
            Assertions.assertEquals(request.getInspection(), entity.getInspection());
        }
    }

    @SuppressWarnings("SameParameterValue")
    public void assertEntitiesAreFullyEqual(Consultation expected, Consultation actual) {
        Assertions.assertEquals(expected.getVisit(), actual.getVisit());
        Assertions.assertEquals(expected.getNumber(), actual.getNumber());
        Assertions.assertEquals(expected.getAnamnesis(), actual.getAnamnesis());
        Assertions.assertEquals(expected.getInspection(), actual.getInspection());
    }
}
