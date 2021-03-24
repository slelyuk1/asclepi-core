package com.jupiter.asclepi.core.model.entity.disease.diagnosis;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import lombok.Data;

import java.io.Serializable;

@Data
public class DiagnosisId implements Serializable {
    // todo use only basic types
    private DiseaseHistoryId diseaseHistory;
    private Integer number;
}
