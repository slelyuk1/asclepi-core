package com.jupiter.asclepi.core.model.model.entity.disease.visit;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.support.mapstruct.ConstructorProperties;
import com.jupiter.asclepi.core.model.support.mapstruct.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class VisitId implements Serializable {
    private DiseaseHistoryId diseaseHistory;
    private Integer number;

    @Default
    @ConstructorProperties
    public VisitId(DiseaseHistoryId diseaseHistory, Integer number) {
        this.diseaseHistory = diseaseHistory;
        this.number = number;
    }
}
