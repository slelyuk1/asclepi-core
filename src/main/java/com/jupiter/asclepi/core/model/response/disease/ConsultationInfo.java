package com.jupiter.asclepi.core.model.response.disease;

import com.jupiter.asclepi.core.helper.object.api.AbstractCreationAware;
import lombok.Value;

import java.math.BigInteger;

@Value
public class ConsultationInfo extends AbstractCreationAware<Integer> {
    BigInteger visitId;
    BigInteger anamnesisId;
    String inspection;
}
