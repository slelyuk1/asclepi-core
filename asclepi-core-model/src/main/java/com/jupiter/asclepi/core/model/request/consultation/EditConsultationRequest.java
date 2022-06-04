package com.jupiter.asclepi.core.model.request.consultation;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class EditConsultationRequest {
    @Valid
    @NotNull
    private GetConsultationRequest consultation;
    private BigInteger anamnesisId;
    private String inspection;
}
