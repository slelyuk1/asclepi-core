package com.jupiter.asclepi.core.model.response.disease;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import lombok.Value;

import java.math.BigInteger;

@Value
public class AnalysisInfo extends AbstractCreationAware<Integer> {
    BigInteger id;
    BigInteger visitId;
    String summary;
}
