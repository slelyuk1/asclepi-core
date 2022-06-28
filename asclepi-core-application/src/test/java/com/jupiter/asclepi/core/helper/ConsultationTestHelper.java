package com.jupiter.asclepi.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.request.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.request.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.model.request.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.repository.entity.Anamnesis;
import com.jupiter.asclepi.core.repository.entity.consultation.Consultation;
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

import java.util.Objects;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RequiredArgsConstructor
public class ConsultationTestHelper {
    private static final String TEST_INSPECTION = "testInspection";

    private final ObjectMapper objectMapper;

    public CreateConsultationRequest generateCreateRequest(Visit visit, Anamnesis anamnesis) {
        CreateConsultationRequest request = new CreateConsultationRequest();
        GetVisitRequest visitGetter = new GetVisitRequest(
                new GetDiseaseHistoryRequest(visit.getDiseaseHistory().getClient().getId(), visit.getDiseaseHistory().getId().getNumber()),
                visit.getId().getNumber()
        );
        request.setVisit(visitGetter);
        request.setAnamnesisId(anamnesis.getId());
        request.setInspection(TEST_INSPECTION);
        return request;
    }

    public CreateConsultationRequest generateAnotherCreateRequest(CreateConsultationRequest request) {
        CreateConsultationRequest another = new CreateConsultationRequest();
        BeanUtils.copyProperties(request, another);
        another.setInspection(another.getInspection() + "Other");
        return another;
    }

    public EditConsultationRequest generateEditRequest(Consultation consultation) {
        DiseaseHistory history = consultation.getVisit().getDiseaseHistory();
        EditConsultationRequest request = new EditConsultationRequest();
        GetConsultationRequest getter = new GetConsultationRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getId().getNumber()),
                        consultation.getVisit().getId().getNumber()
                ),
                consultation.getId().getNumber()
        );
        request.setConsultation(getter);
        request.setAnamnesisId(consultation.getAnamnesis().getId());
        request.setInspection(consultation.getInspection() + "Other");
        return request;
    }

    public MockHttpServletRequestBuilder createMockedCreateRequest(CreateConsultationRequest request) throws JsonProcessingException {
        return post("/api/v1/consultation/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedEditRequest(EditConsultationRequest request) throws JsonProcessingException {
        return patch("/api/v1/consultation/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetRequest(GetConsultationRequest request) throws JsonProcessingException {
        return get("/api/v1/consultation/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedDeleteRequest(GetConsultationRequest request) throws JsonProcessingException {
        return delete("/api/v1/consultation/")
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
                        entity.getVisit().getDiseaseHistory().getId().getNumber()
                ),
                entity.getVisit().getId().getNumber()
        );
        Assertions.assertEquals(request.getVisit(), toCompare);
        Assertions.assertEquals(request.getAnamnesisId(), entity.getAnamnesis().getId());
        Assertions.assertEquals(request.getInspection(), entity.getInspection());
        CreationData<String> creation = entity.getCreation();
        Assertions.assertNotNull(creation);
        Assertions.assertNotNull(creation.getWhen());
        String currentLogin = SecurityUtils.getPrincipal(UserDetails.class)
                .map(UserDetails::getUsername)
                .orElse(null);
        Assertions.assertNotNull(currentLogin);
        Assertions.assertEquals(currentLogin, creation.getBy());
    }

    public void assertEntityIsValidAfterEdition(EditConsultationRequest request, Consultation entity) {
        DiseaseHistory history = entity.getVisit().getDiseaseHistory();
        GetConsultationRequest toCompare = new GetConsultationRequest(
                new GetVisitRequest(
                        new GetDiseaseHistoryRequest(history.getClient().getId(), history.getId().getNumber()),
                        entity.getVisit().getId().getNumber()
                ),
                entity.getId().getNumber()
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
        Assertions.assertEquals(expected.getId().getNumber(), actual.getId().getNumber());
        Assertions.assertEquals(expected.getAnamnesis(), actual.getAnamnesis());
        Assertions.assertEquals(expected.getInspection(), actual.getInspection());
    }
}
