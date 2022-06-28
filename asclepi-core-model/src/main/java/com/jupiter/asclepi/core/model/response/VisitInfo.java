package com.jupiter.asclepi.core.model.response;

import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.common.CreationInfo;

import java.time.LocalDateTime;

public record VisitInfo(
        GetVisitRequest visit,
        LocalDateTime when,
        CreationInfo creation
) {
}
