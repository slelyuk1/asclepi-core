package com.jupiter.asclepi.core.model.request.disease.visit;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class EditVisitRequest {
    @Valid
    @NotNull
    private GetVisitRequest visit;

    @NotNull
    private LocalDateTime when;
}
