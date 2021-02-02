package com.jupiter.asclepi.core.model.request.disease.consultation;

import lombok.Data;

import java.math.BigInteger;

@Data
public class EditConsultationRequest {
    private BigInteger anamnesisId;
    private String inspection;
}
