package com.jupiter.asclepi.core.model.entity.disease.consultation;

import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.entity.disease.visit.VisitId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationId implements Serializable {
    private VisitId visit;
    private Integer number;
}
