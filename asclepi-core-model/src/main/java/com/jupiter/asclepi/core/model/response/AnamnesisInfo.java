package com.jupiter.asclepi.core.model.response;

import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;

import java.math.BigInteger;

public record AnamnesisInfo(
        BigInteger id,
        GetDiseaseHistoryRequest diseaseHistory,
        String complaints,
        String vitae,
        String morbi
) {
}
