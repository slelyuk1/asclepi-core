package com.jupiter.asclepi.core.model.response;

import com.jupiter.asclepi.core.model.request.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.model.response.common.CreationInfo;

import java.math.BigInteger;

public record ConsultationInfo(
        GetConsultationRequest consultation,
        BigInteger anamnesisId,
        String inspection,
        CreationInfo creation
) {
}
