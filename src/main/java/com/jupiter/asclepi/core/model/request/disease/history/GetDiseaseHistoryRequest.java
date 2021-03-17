package com.jupiter.asclepi.core.model.request.disease.history;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class GetDiseaseHistoryRequest {
    @NotNull
    private BigInteger clientId;
    @NotNull
    private Integer number;
}
