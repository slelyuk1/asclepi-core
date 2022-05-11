package com.jupiter.asclepi.core.model.model.entity.disease.diagnosis;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.support.mapstruct.ConstructorProperties;
import com.jupiter.asclepi.core.model.support.mapstruct.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
public class DiagnosisId implements Serializable {

    private BigInteger clientId;
    private Integer diseaseHistoryNumber;
    private Integer number;

    @Default
    @ConstructorProperties
    public DiagnosisId(DiseaseHistoryId diseaseHistory, Integer number) {
        clientId = diseaseHistory.getClient();
        diseaseHistoryNumber = diseaseHistory.getNumber();
        this.number = number;
    }

}
