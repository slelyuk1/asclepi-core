package com.jupiter.asclepi.core.model.request.disease.history;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CreateDiseaseHistoryRequest implements Cloneable {
    private BigInteger clientId;
    private Integer doctorId;

    @Override
    public CreateDiseaseHistoryRequest clone() {
        try {
            return (CreateDiseaseHistoryRequest) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("CloneNotSupportedException cannot be thrown here", e);
        }
    }
}
