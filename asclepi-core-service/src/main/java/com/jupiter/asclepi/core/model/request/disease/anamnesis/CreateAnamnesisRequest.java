package com.jupiter.asclepi.core.model.request.disease.anamnesis;

import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import lombok.Data;
import lombok.SneakyThrows;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateAnamnesisRequest implements Cloneable {
    @Valid
    @NotNull
    private GetDiseaseHistoryRequest diseaseHistory;

    @NotEmpty
    private String complaints;

    @NotEmpty
    private String morbi;

    @NotEmpty
    private String vitae;

    @SneakyThrows
    @Override
    public CreateAnamnesisRequest clone() {
        return (CreateAnamnesisRequest) super.clone();
    }
}
