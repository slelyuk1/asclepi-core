package com.jupiter.asclepi.core.model.model.entity.disease.visit;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.support.mapstruct.ConstructorProperties;
import com.jupiter.asclepi.core.model.support.mapstruct.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class VisitId implements Serializable {
    @Embedded
    private DiseaseHistoryId diseaseHistoryId;
    private Integer number;

    @Default
    @ConstructorProperties
    public VisitId(DiseaseHistoryId diseaseHistoryId, Integer number) {
        this.diseaseHistoryId = diseaseHistoryId;
        this.number = number;
    }
}
