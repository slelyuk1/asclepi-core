package com.jupiter.asclepi.core.model.request.disease.visit;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EditVisitRequest {
    private GetVisitRequest visit;
    private LocalDateTime when;
}
