package com.jupiter.asclepi.core.model.entity.disease;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Analysis extends AbstractCreationAware<Employee> {

    @EqualsAndHashCode.Include
    private Visit visit;

    @EqualsAndHashCode.Include
    private Integer number;

    private String title;

    private String summary;

    // todo when documents functionality is implemented
    // private List<Document> documents;
}
