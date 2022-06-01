package com.jupiter.asclepi.core.model.model.entity.disease.consultation;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.VisitId;
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
public class ConsultationId implements Serializable {
    @Embedded
    private VisitId visitId;
    private Integer number;

    @Default
    @ConstructorProperties
    public ConsultationId(VisitId visitId, Integer number) {
        this.visitId = visitId;
        this.number = number;
    }
}
