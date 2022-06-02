package com.jupiter.asclepi.core.model.response.disease;

import lombok.Value;

import java.math.BigInteger;
import java.nio.file.Path;

@Value
public class DocumentInfo {
    BigInteger id;
    BigInteger diseaseHistoryId;
    BigInteger analysisId;
    Path path;
    String description;
}
