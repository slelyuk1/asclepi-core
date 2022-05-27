package com.jupiter.asclepi.core.model.model.entity.disease.visit;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.support.mapstruct.ConstructorProperties;
import com.jupiter.asclepi.core.model.support.mapstruct.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
public class VisitId implements Serializable {
    private BigInteger clientId;
    private Integer diseaseHistoryNumber;
    private Integer number;

    @Default
    @ConstructorProperties
    public VisitId(DiseaseHistoryId diseaseHistory, Integer number) {
        clientId = diseaseHistory.getClientId();
        diseaseHistoryNumber = diseaseHistory.getNumber();
        this.number = number;
    }
}
