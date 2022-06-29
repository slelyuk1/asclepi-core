package com.jupiter.asclepi.core.model.request.document;

import com.jupiter.asclepi.core.model.request.analysis.GetAnalysisRequest;
import lombok.Data;

import java.nio.file.Path;

@Data
public class CreateDocumentRequest {
    private Path path;
    private String description;
    private GetAnalysisRequest analysis;
}
