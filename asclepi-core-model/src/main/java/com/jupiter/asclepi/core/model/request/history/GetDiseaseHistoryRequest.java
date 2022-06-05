package com.jupiter.asclepi.core.model.request.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDiseaseHistoryRequest {
    @NotNull
    private BigInteger clientId;
    @NotNull
    private Integer number;

}
