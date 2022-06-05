package com.jupiter.asclepi.core.model.response;

import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;

import java.time.LocalDateTime;

public record VisitInfo(
        GetVisitRequest visit,
        LocalDateTime when

        // todo created when and by
) {
}
