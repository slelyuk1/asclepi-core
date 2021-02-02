package com.jupiter.asclepi.core.model.entity.disease;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Diagnosis {
    private BigInteger id;
    private DiseaseHistory diseaseHistory;
    private String disease;
    private String complications;
    private String etiologyAndPathogenesis;
}
