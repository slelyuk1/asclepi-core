package com.jupiter.asclepi.core.model.request.disease.document;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CreateDocumentRequest {
    private BigInteger diseaseHistoryId;
    private BigInteger analysisId;
    private String path;
    private String description;
}
