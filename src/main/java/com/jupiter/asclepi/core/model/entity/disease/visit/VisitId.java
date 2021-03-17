package com.jupiter.asclepi.core.model.entity.disease.visit;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import lombok.Data;

import java.io.Serializable;

@Data
public class VisitId implements Serializable {
    private DiseaseHistoryId diseaseHistory;
    private Integer number;
}
