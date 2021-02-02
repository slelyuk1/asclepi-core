package com.jupiter.asclepi.core.model.response.disease;

import com.jupiter.asclepi.core.helper.object.api.AbstractCreationAware;
import lombok.Value;

import java.math.BigInteger;
import java.util.Date;

@Value
public class VisitInfo extends AbstractCreationAware<Integer> {
    BigInteger diseaseHistoryId;
    Integer number;
    Date when;
}
