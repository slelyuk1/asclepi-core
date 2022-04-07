package com.jupiter.asclepi.core.model.model.request.disease.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDiseaseHistoryRequest implements Cloneable {
    @NotNull
    private BigInteger clientId;
    @NotNull
    private Integer number;

    @SneakyThrows
    @Override
    public GetDiseaseHistoryRequest clone() {
        return (GetDiseaseHistoryRequest) super.clone();
    }
}
