package com.jupiter.asclepi.core.model.entity.analysis;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.entity.disease.visit.VisitId;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
public class AnalysisId implements Serializable {

    @NotNull
    private BigInteger clientId;
    @NotNull
    private Integer diseaseHistoryNumber;
    @NotNull
    private Integer visitNumber;
    @NotNull
    private Integer number;

    public AnalysisId(DiseaseHistoryId diseaseHistory, VisitId visitId, Integer number) {
        clientId = diseaseHistory.getClient();
        diseaseHistoryNumber = diseaseHistory.getNumber();
        visitNumber = visitId.getNumber();
        this.number = number;
    }
}
