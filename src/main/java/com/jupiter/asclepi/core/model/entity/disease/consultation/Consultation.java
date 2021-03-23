package com.jupiter.asclepi.core.model.entity.disease.consultation;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@IdClass(ConsultationId.class)
public class Consultation extends AbstractCreationAware<Employee> {

    @Id
    @ManyToOne
    @EqualsAndHashCode.Include
    private Visit visit;

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Integer number;

    @ManyToOne
    private Anamnesis anamnesis;

    @NotEmpty
    private String inspection;

    public Consultation(@NotNull Visit visit, @NotNull Integer number) {
        this.visit = visit;
        this.number = number;
    }
}
