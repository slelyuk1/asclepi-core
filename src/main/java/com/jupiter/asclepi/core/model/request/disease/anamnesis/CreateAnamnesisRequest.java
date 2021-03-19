package com.jupiter.asclepi.core.model.request.disease.anamnesis;

import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class CreateAnamnesisRequest implements Cloneable {
    private GetDiseaseHistoryRequest diseaseHistory;
    private String complaints;
    private String morbi;
    private String vitae;

    @SneakyThrows
    @Override
    public CreateAnamnesisRequest clone() {
        return (CreateAnamnesisRequest) super.clone();
    }
}
