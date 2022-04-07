package com.jupiter.asclepi.core.model.model.response.disease;

import com.jupiter.asclepi.core.model.model.request.disease.consultation.GetConsultationRequest;
import lombok.Value;

import java.math.BigInteger;

@Value
public class ConsultationInfo {
    GetConsultationRequest consultation;
    BigInteger anamnesisId;
    String inspection;

    // todo created by and when
}
