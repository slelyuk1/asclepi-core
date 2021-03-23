package com.jupiter.asclepi.core.model.entity.disease.visit;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitId implements Serializable {
    private DiseaseHistoryId diseaseHistory;
    private Integer number;
}
