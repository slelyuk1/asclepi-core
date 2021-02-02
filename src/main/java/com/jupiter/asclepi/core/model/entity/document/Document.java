package com.jupiter.asclepi.core.model.entity.document;

import com.jupiter.asclepi.core.model.entity.disease.Analysis;
import com.jupiter.asclepi.core.model.entity.disease.DiseaseHistory;
import lombok.Data;

import java.math.BigInteger;

@Data
public class Document {
    private BigInteger id;
    private DiseaseHistory diseaseHistory;
    private Analysis analysis;
    private String path;
    private String description;
}
