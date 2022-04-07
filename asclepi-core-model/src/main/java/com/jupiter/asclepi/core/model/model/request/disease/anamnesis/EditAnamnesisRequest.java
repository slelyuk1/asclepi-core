package com.jupiter.asclepi.core.model.model.request.disease.anamnesis;

import lombok.Data;

import java.math.BigInteger;

@Data
public class EditAnamnesisRequest {
    private BigInteger id;
    private String complaints;
    private String morbi;
    private String vitae;
}
