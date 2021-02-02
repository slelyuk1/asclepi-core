package com.jupiter.asclepi.core.model.request.disease.visit;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
public class CreateVisitRequest {
    private BigInteger diseaseHistoryId;
    private Date when;
}
