package com.jupiter.asclepi.core.model.request.analysis;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class EditAnalysisRequest {
    @Valid
    @NotNull
    private GetAnalysisRequest analysis;
    private String title;
    private String summary;
}
