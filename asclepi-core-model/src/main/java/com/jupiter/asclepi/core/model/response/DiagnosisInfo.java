package com.jupiter.asclepi.core.model.response;

import com.jupiter.asclepi.core.model.request.diagnosis.GetDiagnosisRequest;

public record DiagnosisInfo(
        GetDiagnosisRequest diagnosis,
        String disease,
        String complications,
        String etiologyAndPathogenesis,
        String specialityOfCourse,
        Boolean isFinal
) {
}
