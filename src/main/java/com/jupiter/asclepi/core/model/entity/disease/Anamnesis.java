package com.jupiter.asclepi.core.model.entity.disease;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import lombok.Data;

import java.math.BigInteger;

@Data
public class Anamnesis {
    private BigInteger id;
    private DiseaseHistory diseaseHistory;
    private String complaints;
    private String morbi;
    private String vitae;
}
