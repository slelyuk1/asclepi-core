package com.jupiter.asclepi.core.model.request.disease.visit;

import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import lombok.Data;
import lombok.SneakyThrows;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CreateVisitRequest implements Cloneable {
    @Valid
    @NotNull
    private GetDiseaseHistoryRequest diseaseHistory;

    @NotNull
    private LocalDateTime when;

    @SneakyThrows
    @Override
    public CreateVisitRequest clone() {
        return (CreateVisitRequest) super.clone();
    }
}
