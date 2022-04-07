package com.jupiter.asclepi.core.model.model.entity.disease.consultation;

import com.jupiter.asclepi.core.model.model.entity.disease.visit.VisitId;
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

    public ConsultationId(VisitId visitId, Integer number) {
        clientId = visitId.getClientId();
        diseaseHistoryNumber = visitId.getDiseaseHistoryNumber();
        visitNumber = visitId.getNumber();
        this.number = number;
    }
}
