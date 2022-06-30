package com.jupiter.asclepi.core.model.response;

import com.jupiter.asclepi.core.model.request.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.response.common.CreationInfo;

import java.util.List;

public record AnalysisInfo(
        GetAnalysisRequest analysis,
        String title,
        String summary,
        List<DocumentInfo> documents,
        CreationInfo creation
) {
}
