package com.jupiter.asclepi.core.model.entity.disease;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import lombok.Data;

import java.util.Date;

@Data
public class Visit extends AbstractCreationAware<Employee> {
    private DiseaseHistory diseaseHistory;
    private Integer number;
    private Date when;
}
