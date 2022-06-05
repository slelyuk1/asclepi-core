package com.jupiter.asclepi.core.model.request.diagnosis;

import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import lombok.Data;

@Data
public class CreateDiagnosisRequest {
    private GetDiseaseHistoryRequest diseaseHistory;
    private String disease;
    private Boolean isFinal;
    private String complications;
    private String etiologyAndPathogenesis;
    private String specialityOfCourse;
}
