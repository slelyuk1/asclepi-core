package com.jupiter.asclepi.core.model.response;

import java.math.BigInteger;
import java.nio.file.Path;

public record DocumentInfo(
        BigInteger id,
        BigInteger diseaseHistoryId,
        BigInteger analysisId,
        Path path,
        String description
) {
}
