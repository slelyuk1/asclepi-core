package com.jupiter.asclepi.core.model.request.disease.diagnosis;

import lombok.Data;

import java.math.BigInteger;

@Data
public class EditDiagnosisRequest {
    private BigInteger diseaseHistoryId;
    private String disease;
    private String complications;
    private String concomitantPathology;
}
