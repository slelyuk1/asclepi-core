package com.jupiter.asclepi.core.model.request.disease.consultation;

import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import lombok.Data;
import lombok.SneakyThrows;

import java.math.BigInteger;

@Data
public class CreateConsultationRequest implements Cloneable {
    private GetVisitRequest visit;
    private BigInteger anamnesisId;
    private String inspection;

    @SneakyThrows
    @Override
    public CreateConsultationRequest clone() {
        CreateConsultationRequest cloned = (CreateConsultationRequest) super.clone();
        cloned.visit = visit.clone();
        return cloned;
    }
}
