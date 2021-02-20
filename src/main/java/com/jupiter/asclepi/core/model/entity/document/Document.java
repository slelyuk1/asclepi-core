package com.jupiter.asclepi.core.model.entity.document;

import com.jupiter.asclepi.core.model.entity.disease.Analysis;
import com.jupiter.asclepi.core.model.entity.disease.DiseaseHistory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;
import java.nio.file.Path;

// todo configure disease history when its functionality will be implemented
// todo configure analysis when its functionality will be implemented
// todo configure for persistence (Viktor Muratov) (see com.jupiter.asclepi.core.model.entity.people.Employee)
// todo configure validation (Viktor Muratov)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Document {
    @EqualsAndHashCode.Include
    private BigInteger id;
    // todo write a converter like com.jupiter.asclepi.core.model.entity.converter.RoleConverter (Viktor Muratov)
    private Path path;
    private String description;
}
