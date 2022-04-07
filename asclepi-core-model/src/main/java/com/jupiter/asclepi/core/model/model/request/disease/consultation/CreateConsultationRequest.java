package com.jupiter.asclepi.core.model.model.request.disease.consultation;

import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import lombok.Data;
import lombok.SneakyThrows;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class CreateConsultationRequest implements Cloneable {
    @Valid
    @NotNull
    private GetVisitRequest visit;
    @NotNull
    private BigInteger anamnesisId;
    @NotEmpty
    private String inspection;

    @SneakyThrows
    @Override
    public CreateConsultationRequest clone() {
        CreateConsultationRequest cloned = (CreateConsultationRequest) super.clone();
        cloned.visit = visit.clone();
        return cloned;
    }
}
