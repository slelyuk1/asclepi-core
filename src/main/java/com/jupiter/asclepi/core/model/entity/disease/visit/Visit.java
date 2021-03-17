package com.jupiter.asclepi.core.model.entity.disease.visit;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Visit extends AbstractCreationAware<Employee> {
    private VisitId id;
    private LocalDateTime when;
}
