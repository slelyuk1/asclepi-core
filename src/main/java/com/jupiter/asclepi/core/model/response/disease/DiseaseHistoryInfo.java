package com.jupiter.asclepi.core.model.response.disease;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import lombok.Value;

import java.math.BigInteger;

@Value
public class DiseaseHistoryInfo extends AbstractCreationAware<Integer> {
    Integer clientId;
    Integer number;
    BigInteger diagnosisId;
    Integer doctorId;
}
