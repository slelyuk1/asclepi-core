package com.jupiter.asclepi.core.repository.entity.consultation;

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
public class ConsultationId implements Serializable {
    @Embedded
    private VisitId visitId;
    private Integer number;
}
