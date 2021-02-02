package com.jupiter.asclepi.core.model.request.disease.diagnosis;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CreateDiagnosisRequest {
    private BigInteger diseaseHistoryId;
    private String disease;
    private String complications;
    private String concomitantPathology;
}
