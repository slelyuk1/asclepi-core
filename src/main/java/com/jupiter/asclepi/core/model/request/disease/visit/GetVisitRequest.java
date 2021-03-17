package com.jupiter.asclepi.core.model.request.disease.visit;

import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class GetVisitRequest {
    @Valid
    @NotNull
    private GetDiseaseHistoryRequest diseaseHistory;

    @NotNull
    private Integer number;
}
