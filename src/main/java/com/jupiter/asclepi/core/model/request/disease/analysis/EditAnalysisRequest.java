package com.jupiter.asclepi.core.model.request.disease.analysis;

import lombok.Data;

@Data
public class EditAnalysisRequest {
    private GetAnalysisRequest analysis;
    private String title;
    private String summary;
}
