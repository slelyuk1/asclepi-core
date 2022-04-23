package com.jupiter.asclepi.core.model.model.entity.disease.diagnosis;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.support.mapstruct.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
public class DiagnosisId implements Serializable {

    @NotNull
    private BigInteger clientId;
    @NotNull
    private Integer diseaseHistoryNumber;
    @NotNull
    private Integer number;

    @Default
    public DiagnosisId(@NotNull DiseaseHistoryId diseaseHistory, @NotNull Integer number) {
        clientId = diseaseHistory.getClient();
        diseaseHistoryNumber = diseaseHistory.getNumber();
        this.number = number;
    }

}
