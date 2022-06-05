package com.jupiter.asclepi.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.asclepi.core.model.request.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.entity.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Objects;
import java.util.Optional;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RequiredArgsConstructor
public class DiagnosisTestHelper {
    private static final String TEST_DISEASE = "testDisease";
    private static final String TEST_COMPLICATIONS = "testComplications";
    private static final String TEST_ETIOLOGY_AND_PATHOGENESIS = "testEtiologyAndPathogenesis";
    private static final String TEST_SPECIALTY_OF_COURSE = "testSpecialtyOfCourse";

    private final ObjectMapper objectMapper;

    public CreateDiagnosisRequest generateCreateRequest(DiseaseHistory history, boolean withOptional) {
        CreateDiagnosisRequest request = new CreateDiagnosisRequest();
        GetDiseaseHistoryRequest getter = new GetDiseaseHistoryRequest(history.getClient().getId(), history.getId().getNumber());
        request.setDiseaseHistory(getter);
        request.setDisease(TEST_DISEASE);
        request.setIsFinal(false);
        if (withOptional) {
            request.setComplications(TEST_COMPLICATIONS);
            request.setEtiologyAndPathogenesis(TEST_ETIOLOGY_AND_PATHOGENESIS);
            request.setSpecialityOfCourse(TEST_SPECIALTY_OF_COURSE);
        }
        return request;
    }

    public CreateDiagnosisRequest generateAnotherCreateRequest(CreateDiagnosisRequest request, boolean withOptional) {
        CreateDiagnosisRequest another = request.clone();
        request.setDisease(request.getDisease() + "Other");
        request.setIsFinal(!request.getIsFinal());
        if (withOptional) {
            request.setComplications(Optional.ofNullable(request.getComplications()).map(c -> c + "Other").orElse("Other"));
            request.setEtiologyAndPathogenesis(Optional.ofNullable(request.getEtiologyAndPathogenesis()).map(c -> c + "Other").orElse("Other"));
            request.setSpecialityOfCourse(Optional.ofNullable(request.getSpecialityOfCourse()).map(c -> c + "Other").orElse("Other"));
        }
        return another;
    }

    public EditDiagnosisRequest generateEditRequest(Diagnosis diagnosis, boolean withOptional) {
        EditDiagnosisRequest request = new EditDiagnosisRequest();
        DiseaseHistory history = diagnosis.getDiseaseHistory();
        GetDiagnosisRequest getter = new GetDiagnosisRequest(
                new GetDiseaseHistoryRequest(history.getClient().getId(), history.getId().getNumber()),
                diagnosis.getId().getNumber()
        );
        request.setDiagnosis(getter);
        request.setDisease(diagnosis.getDisease() + "Other");
        request.setIsFinal(!diagnosis.getIsFinal());
        if (withOptional) {
            request.setComplications(Optional.ofNullable(diagnosis.getComplications()).map(c -> c + "Other").orElse("Other"));
            request.setEtiologyAndPathogenesis(Optional.ofNullable(diagnosis.getEtiologyAndPathogenesis()).map(c -> c + "Other").orElse("Other"));
            request.setSpecialityOfCourse(Optional.ofNullable(diagnosis.getSpecialityOfCourse()).map(c -> c + "Other").orElse("Other"));
        }
        return request;
    }

    public MockHttpServletRequestBuilder createMockedCreateRequest(CreateDiagnosisRequest request) throws JsonProcessingException {
        return post("/api/v1/diagnosis/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedEditRequest(EditDiagnosisRequest request) throws JsonProcessingException {
        return post("/api/v1/diagnosis/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedDeleteRequest(GetDiagnosisRequest request) throws JsonProcessingException {
        return delete("/api/v1/diagnosis/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetRequest(GetDiagnosisRequest request) throws JsonProcessingException {
        return get("/api/v1/diagnosis/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public MockHttpServletRequestBuilder createMockedGetAllRequest() {
        return get("/api/v1/diagnosis/all");
    }

    public MockHttpServletRequestBuilder createMockedGetForDiseaseHistory(GetDiseaseHistoryRequest request) throws JsonProcessingException {
        return get("/api/v1/diagnosis/getForDiseaseHistory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public void assertEntityIsValidAfterCreation(CreateDiagnosisRequest request, Diagnosis entity) {
        DiseaseHistory history = entity.getDiseaseHistory();
        GetDiseaseHistoryRequest toCompare = new GetDiseaseHistoryRequest(history.getClient().getId(), history.getId().getNumber());
        Assertions.assertEquals(request.getDiseaseHistory(), toCompare);
        Assertions.assertEquals(request.getDisease(), entity.getDisease());
        Assertions.assertEquals(request.getIsFinal(), entity.getIsFinal());
        Assertions.assertEquals(request.getComplications(), entity.getComplications());
        Assertions.assertEquals(request.getEtiologyAndPathogenesis(), entity.getEtiologyAndPathogenesis());
        Assertions.assertEquals(request.getSpecialityOfCourse(), entity.getSpecialityOfCourse());
    }

    public void assertEntityIsValidAfterEdition(EditDiagnosisRequest request, Diagnosis entity) {
        DiseaseHistory history = entity.getDiseaseHistory();
        GetDiagnosisRequest toCompare = new GetDiagnosisRequest(
                new GetDiseaseHistoryRequest(history.getClient().getId(), history.getId().getNumber()),
                entity.getId().getNumber()
        );
        Assertions.assertEquals(request.getDiagnosis(), toCompare);
        Assertions.assertEquals(request.getDisease(), entity.getDisease());
        Assertions.assertEquals(request.getIsFinal(), entity.getIsFinal());
        if (Objects.nonNull(request.getComplications())) {
            Assertions.assertEquals(request.getComplications(), entity.getComplications());
        }
        if (Objects.nonNull(request.getEtiologyAndPathogenesis())) {
            Assertions.assertEquals(request.getEtiologyAndPathogenesis(), entity.getEtiologyAndPathogenesis());
        }
        if (Objects.nonNull(request.getSpecialityOfCourse())) {
            Assertions.assertEquals(request.getSpecialityOfCourse(), entity.getSpecialityOfCourse());
        }
    }

    @SuppressWarnings("SameParameterValue")
    public void assertEntitiesAreFullyEqual(Diagnosis expected, Diagnosis actual) {
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected.getDisease(), actual.getDisease());
        Assertions.assertEquals(expected.getIsFinal(), actual.getIsFinal());
        Assertions.assertEquals(expected.getComplications(), actual.getComplications());
        Assertions.assertEquals(expected.getEtiologyAndPathogenesis(), actual.getEtiologyAndPathogenesis());
    }
}
