package com.jupiter.asclepi.core.model.response.disease;

import lombok.Value;

import java.math.BigInteger;

@Value
public class AnamnesisInfo {
    BigInteger id;
    BigInteger diseaseHistoryId;
    String complaints;
    String morbi;
    String vitae;
}
