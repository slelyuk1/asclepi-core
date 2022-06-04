package com.jupiter.asclepi.core.repository.entity.analysis;

import com.jupiter.asclepi.core.repository.entity.visit.VisitId;
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
public class AnalysisId implements Serializable {
    @Embedded
    private VisitId visitId;
    private Integer number;
}
