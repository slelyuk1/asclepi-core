package com.jupiter.asclepi.core.model.model.request.disease.analysis;

import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAnalysisRequest {
    @NotNull
    @Valid
    private GetVisitRequest visit;
    @NotNull
    private Integer number;
}
