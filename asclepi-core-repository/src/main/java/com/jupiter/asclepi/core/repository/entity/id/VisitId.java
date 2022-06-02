package com.jupiter.asclepi.core.repository.entity.id;

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
public class VisitId implements Serializable {
    @Embedded
    private DiseaseHistoryId diseaseHistoryId;
    private Integer number;
}
