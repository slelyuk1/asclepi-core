package com.jupiter.asclepi.core.model.response;

import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class VisitInfo {
    GetVisitRequest visit;
    LocalDateTime when;

    // todo created when and by
}
