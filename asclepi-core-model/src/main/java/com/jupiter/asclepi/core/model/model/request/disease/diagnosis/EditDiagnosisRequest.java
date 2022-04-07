package com.jupiter.asclepi.core.model.model.request.disease.diagnosis;

import lombok.Data;

@Data
public class EditDiagnosisRequest {
    private GetDiagnosisRequest diagnosis;
    private Boolean isFinal;
    private String disease;
    private String complications;
    private String etiologyAndPathogenesis;
    private String specialityOfCourse;
}
