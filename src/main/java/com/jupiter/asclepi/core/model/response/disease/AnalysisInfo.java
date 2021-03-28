package com.jupiter.asclepi.core.model.response.disease;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.request.disease.analysis.GetAnalysisRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import lombok.Value;

@Value
public class AnalysisInfo {
    GetAnalysisRequest analysis;
    String title;
    String summary;

    // todo created when and by
}
