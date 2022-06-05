package com.jupiter.asclepi.core.model.request.anamnesis;

import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import lombok.Data;
import lombok.SneakyThrows;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateAnamnesisRequest {
    @Valid
    @NotNull
    private GetDiseaseHistoryRequest diseaseHistory;

    @NotEmpty
    private String complaints;

    @NotEmpty
    private String morbi;

    @NotEmpty
    private String vitae;

}
