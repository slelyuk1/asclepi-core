package com.jupiter.asclepi.core.repository.entity.diagnosis;

import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistoryId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DiagnosisId implements Serializable {
    @Embedded
    private DiseaseHistoryId diseaseHistoryId;
    private Integer number;
}
