package com.jupiter.asclepi.core.model.request.disease.consultation;

import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetConsultationRequest {
    private GetVisitRequest visit;
    private Integer number;
}
