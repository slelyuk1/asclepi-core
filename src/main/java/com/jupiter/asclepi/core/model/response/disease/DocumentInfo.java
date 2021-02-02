package com.jupiter.asclepi.core.model.response.disease;

import lombok.Value;

import java.math.BigInteger;

@Value
public class DocumentInfo {
    BigInteger id;
    BigInteger diseaseHistoryId;
    BigInteger analysisId;
    String path;
    String description;
}
