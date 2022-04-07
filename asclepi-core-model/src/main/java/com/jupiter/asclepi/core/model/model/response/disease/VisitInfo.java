package com.jupiter.asclepi.core.model.model.response.disease;

import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class VisitInfo {
    GetVisitRequest visit;
    LocalDateTime when;

    // todo created when and by
}
