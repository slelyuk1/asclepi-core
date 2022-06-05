package com.jupiter.asclepi.core.model.request.history;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class CreateDiseaseHistoryRequest {
    @NotNull
    private BigInteger clientId;
    @NotNull
    private Integer doctorId;

}
