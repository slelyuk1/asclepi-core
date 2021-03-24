package com.jupiter.asclepi.core.model.request.disease.analysis;

import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import lombok.Data;
import lombok.SneakyThrows;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
