package com.jupiter.asclepi.core.model.response.disease;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import lombok.Value;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

@Value
public class VisitInfo extends AbstractCreationAware<Integer> {
    GetVisitRequest visit;
    LocalDateTime when;
}
