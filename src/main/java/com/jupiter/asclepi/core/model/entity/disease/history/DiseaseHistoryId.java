package com.jupiter.asclepi.core.model.entity.disease.history;

import lombok.Data;

import java.math.BigInteger;

@Data
public class DiseaseHistoryId {
    private BigInteger clientId;
    private Integer number;
}
