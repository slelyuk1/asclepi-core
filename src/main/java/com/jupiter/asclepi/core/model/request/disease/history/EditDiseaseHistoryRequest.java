package com.jupiter.asclepi.core.model.request.disease.history;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class EditDiseaseHistoryRequest {
    @Valid
    @NotNull
    private GetDiseaseHistoryRequest diseaseHistory;

    @NotNull
    private Integer doctorId;
}
