package com.jupiter.asclepi.core.model.response.disease;

import lombok.Value;

import java.math.BigInteger;
import java.util.List;

@Value
public class DiseaseHistoryInfo {
    Integer clientId;
    Integer number;
    List<BigInteger> diagnosisIds;
    Integer doctorId;

    // todo created by and created when
}
