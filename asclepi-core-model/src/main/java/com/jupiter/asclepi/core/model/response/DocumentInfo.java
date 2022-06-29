package com.jupiter.asclepi.core.model.response;

import com.jupiter.asclepi.core.model.request.analysis.GetAnalysisRequest;

import java.math.BigInteger;
import java.nio.file.Path;

public record DocumentInfo(
        BigInteger id,
        Path path,
        String description,
        GetAnalysisRequest analysis
) {
}
