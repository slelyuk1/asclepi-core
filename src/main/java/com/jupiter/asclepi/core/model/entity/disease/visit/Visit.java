package com.jupiter.asclepi.core.model.entity.disease.visit;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@IdClass(VisitId.class)
public class Visit extends AbstractCreationAware<Employee> {
    @Id
    @ManyToOne
    @EqualsAndHashCode.Include
    private DiseaseHistory diseaseHistory;

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Integer number;


    @NotNull
    private LocalDateTime when;

    public Visit(DiseaseHistory diseaseHistory, Integer number) {
        this.diseaseHistory = diseaseHistory;
        this.number = number;
    }
}
