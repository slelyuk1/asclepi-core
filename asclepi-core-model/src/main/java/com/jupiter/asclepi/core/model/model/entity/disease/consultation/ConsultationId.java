package com.jupiter.asclepi.core.model.model.entity.disease.consultation;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.VisitId;
import com.jupiter.asclepi.core.model.support.mapstruct.ConstructorProperties;
import com.jupiter.asclepi.core.model.support.mapstruct.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
public class ConsultationId implements Serializable {
    private BigInteger clientId;
    private Integer diseaseHistoryNumber;
    private Integer visitNumber;
    private Integer number;

    @Default
    @ConstructorProperties
    public ConsultationId(VisitId visitId, Integer number) {
        DiseaseHistoryId diseaseHistoryId = visitId.getDiseaseHistory();
        clientId = diseaseHistoryId.getClient();
        diseaseHistoryNumber = diseaseHistoryId.getNumber();
        visitNumber = visitId.getNumber();
        this.number = number;
    }
}
