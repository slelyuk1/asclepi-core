package com.jupiter.asclepi.core.model.request.disease.document;

import lombok.Data;

import java.math.BigInteger;
import java.nio.file.Path;

// todo add disease history when its functionality is implemented
// todo add analysis when its functionality functionality is implemented
@Data
public class EditDocumentRequest {
    private BigInteger id;
    private Path path;
    private String description;
}
