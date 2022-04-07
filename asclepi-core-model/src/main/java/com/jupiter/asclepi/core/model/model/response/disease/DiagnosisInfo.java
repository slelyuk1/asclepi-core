package com.jupiter.asclepi.core.model.model.response.disease;

import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.GetDiagnosisRequest;
import lombok.Value;

@Value
public class DiagnosisInfo {
    GetDiagnosisRequest diagnosis;
    String disease;
    String complications;
    String etiologyAndPathogenesis;
    String specialityOfCourse;
    Boolean isFinal;
}
