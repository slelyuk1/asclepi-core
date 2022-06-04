package com.jupiter.asclepi.core.model.response;

import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import lombok.Value;

import java.math.BigInteger;

@Value
public class AnamnesisInfo {
    BigInteger id;
    GetDiseaseHistoryRequest diseaseHistory;
    String complaints;
    String vitae;
    String morbi;
}
