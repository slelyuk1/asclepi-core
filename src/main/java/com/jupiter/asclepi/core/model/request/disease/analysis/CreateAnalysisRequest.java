package com.jupiter.asclepi.core.model.request.disease.analysis;

import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class CreateAnalysisRequest implements Cloneable {
    private GetVisitRequest visit;
    private String title;
    private String summary;

    @SneakyThrows
    @Override
    public CreateAnalysisRequest clone() {
        CreateAnalysisRequest cloned = (CreateAnalysisRequest) super.clone();
        cloned.visit = visit.clone();
        return cloned;
    }
}
