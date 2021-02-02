package com.jupiter.asclepi.core.model.request.disease.consultation;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CreateConsultationRequest {
    private BigInteger visitId;
    private BigInteger anamnesisId;
    private String inspection;
}
