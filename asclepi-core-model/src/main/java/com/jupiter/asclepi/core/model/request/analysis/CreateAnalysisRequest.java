package com.jupiter.asclepi.core.model.request.analysis;

import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import lombok.Data;
import lombok.SneakyThrows;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateAnalysisRequest {

    @Valid
    @NotNull
    private GetVisitRequest visit;
    @NotEmpty
    private String title;
    @NotEmpty
    private String summary;

}
