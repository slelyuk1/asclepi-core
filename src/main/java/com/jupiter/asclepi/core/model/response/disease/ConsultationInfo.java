package com.jupiter.asclepi.core.model.response.disease;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.request.disease.consultation.GetConsultationRequest;
import lombok.Value;

import java.math.BigInteger;

@Value
public class ConsultationInfo extends AbstractCreationAware<Integer> {
    GetConsultationRequest consultation;
    BigInteger anamnesisId;
    String inspection;
}
