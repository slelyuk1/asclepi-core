package com.jupiter.asclepi.core.model.request.disease.history;

import lombok.Data;

import java.math.BigInteger;

@Data
public class EditDiseaseHistoryRequest {
    private BigInteger clientId;
    private Integer number;
    private Integer doctorId;
}
