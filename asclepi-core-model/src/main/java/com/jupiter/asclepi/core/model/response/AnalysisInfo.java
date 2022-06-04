package com.jupiter.asclepi.core.model.response;

import com.jupiter.asclepi.core.model.request.disease.analysis.GetAnalysisRequest;
import lombok.Value;

@Value
public class AnalysisInfo {
    GetAnalysisRequest analysis;
    String title;
    String summary;

    // todo created when and by
}
