package com.jupiter.asclepi.core.model.request.consultation;

import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class CreateConsultationRequest {
    @Valid
    @NotNull
    private GetVisitRequest visit;
    @NotNull
    private BigInteger anamnesisId;
    @NotEmpty
    private String inspection;

}
