package com.jupiter.asclepi.core.model.request.disease.analysis;

import lombok.Data;

@Data
public class EditAnalysisRequest {
    private GetAnalysisRequest visit;
    private Integer number;
    private String title;
    private String summary;
}
