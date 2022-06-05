package com.jupiter.asclepi.core.model.response;

import com.jupiter.asclepi.core.model.request.analysis.GetAnalysisRequest;

// todo created when and by
public record AnalysisInfo(
        GetAnalysisRequest analysis,
        String title,
        String summary
) {
}
