package com.jupiter.asclepi.core.model.request.disease.consultation;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CreateAnalysisRequest {
    private BigInteger visitId;
    private String summary;
}
