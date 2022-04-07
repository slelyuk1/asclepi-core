package com.jupiter.asclepi.core.model.model.request.disease.visit;

import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVisitRequest implements Cloneable {
    @Valid
    @NotNull
    private GetDiseaseHistoryRequest diseaseHistory;

    @NotNull
    private Integer number;

    @SneakyThrows
    @Override
    public GetVisitRequest clone() {
        GetVisitRequest cloned = (GetVisitRequest) super.clone();
        cloned.diseaseHistory = diseaseHistory.clone();
        return cloned;
    }
}
