package com.jupiter.asclepi.core.model.request.disease.anamnesis;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CreateAnamnesisRequest {
    private BigInteger diseaseHistoryId;
    private String complaints;
    private String morbi;
    private String vitae;
}
