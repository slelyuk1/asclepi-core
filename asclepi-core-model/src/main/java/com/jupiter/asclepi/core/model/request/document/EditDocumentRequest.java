package com.jupiter.asclepi.core.model.request.document;

import lombok.Data;

import java.math.BigInteger;

@Data
public class EditDocumentRequest {
    private BigInteger id;
    private String description;
}
