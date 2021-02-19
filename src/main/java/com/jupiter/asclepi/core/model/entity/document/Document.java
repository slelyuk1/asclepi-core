package com.jupiter.asclepi.core.model.entity.document;

import com.jupiter.asclepi.core.model.entity.disease.Analysis;
import com.jupiter.asclepi.core.model.entity.disease.DiseaseHistory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;

// todo configure for persistence (Viktor Muratov) (see com.jupiter.asclepi.core.model.entity.people.Employee)
// todo configure validation (Viktor Muratov)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Document {
    @EqualsAndHashCode.Include
    private BigInteger id;
    private DiseaseHistory diseaseHistory;
    private Analysis analysis;
    private String path;
    private String description;
}
