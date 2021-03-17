package com.jupiter.asclepi.core.model.request.disease.visit;

import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import lombok.Data;
import lombok.SneakyThrows;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class CreateVisitRequest implements Cloneable {
    private GetDiseaseHistoryRequest diseaseHistory;
    private LocalDateTime when;

    @SneakyThrows
    @Override
    public CreateVisitRequest clone() {
        return (CreateVisitRequest) super.clone();
    }
}
