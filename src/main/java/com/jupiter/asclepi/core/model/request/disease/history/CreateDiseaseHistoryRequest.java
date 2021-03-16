package com.jupiter.asclepi.core.model.request.disease.history;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CreateDiseaseHistoryRequest {
    private BigInteger clientId;
    private Integer doctorId;
}
