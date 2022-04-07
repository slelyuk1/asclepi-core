package com.jupiter.asclepi.core.model.request.disease.document;

import lombok.Data;

import java.nio.file.Path;

// todo add disease history when its functionality is implemented
// todo add analysis when its functionality functionality is implemented
@Data
public class CreateDocumentRequest {
    private Path path;
    private String description;
}
