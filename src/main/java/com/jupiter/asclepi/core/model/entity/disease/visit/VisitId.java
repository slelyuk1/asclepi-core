package com.jupiter.asclepi.core.model.entity.disease.visit;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import lombok.Data;

@Data
public class VisitId {
    private DiseaseHistoryId diseaseHistoryId;
    private Integer number;
}
