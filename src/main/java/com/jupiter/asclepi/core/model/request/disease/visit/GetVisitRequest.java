package com.jupiter.asclepi.core.model.request.disease.visit;

import lombok.Data;

import java.math.BigInteger;

@Data
public class GetVisitRequest {
    private BigInteger diseaseHistoryId;
    private Integer number;
}
