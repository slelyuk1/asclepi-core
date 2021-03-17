package com.jupiter.asclepi.core.model.entity.disease.visit;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@IdClass(VisitId.class)
public class Visit extends AbstractCreationAware<Employee> {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Integer number;

    @Id
    @ManyToOne
    @EqualsAndHashCode.Include
    private DiseaseHistory diseaseHistory;


    private LocalDateTime when;
}
