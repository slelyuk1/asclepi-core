package com.jupiter.asclepi.core.model.entity.disease.visit;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
public class VisitId implements Serializable {
    private BigInteger clientId;
    private Integer diseaseHistoryNumber;
    private Integer number;

    public VisitId(DiseaseHistoryId diseaseHistory, Integer number) {
        clientId = diseaseHistory.getClient();
        diseaseHistoryNumber = diseaseHistory.getNumber();
        this.number = number;
    }
}
