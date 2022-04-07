package com.jupiter.asclepi.core.model.model.entity.analysis;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.VisitId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
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

    public AnalysisId(@NotNull DiseaseHistoryId diseaseHistory, @NotNull VisitId visitId, @NotNull Integer number) {
        clientId = diseaseHistory.getClient();
        diseaseHistoryNumber = diseaseHistory.getNumber();
        visitNumber = visitId.getNumber();
        this.number = number;
    }
}
