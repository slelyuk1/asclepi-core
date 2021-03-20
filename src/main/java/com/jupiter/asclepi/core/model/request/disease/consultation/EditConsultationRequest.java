package com.jupiter.asclepi.core.model.request.disease.consultation;

import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import lombok.Data;

import java.math.BigInteger;

@Data
public class EditConsultationRequest {
    private GetConsultationRequest consultation;
    private BigInteger anamnesisId;
    private String inspection;
}
