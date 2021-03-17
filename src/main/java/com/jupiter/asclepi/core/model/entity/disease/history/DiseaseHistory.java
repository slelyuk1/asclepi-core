package com.jupiter.asclepi.core.model.entity.disease.history;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.entity.disease.Diagnosis;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@IdClass(DiseaseHistoryId.class)
public class DiseaseHistory extends AbstractCreationAware<Employee> {

    @Id
    @EqualsAndHashCode.Include
    private BigInteger clientId;

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Integer number;

    @NotNull
    @ManyToOne
    private Employee doctor;

    @Setter(AccessLevel.PRIVATE)
    @NotNull
    @OneToMany
    List<Diagnosis> diagnoses;

    public DiseaseHistory() {
        diagnoses = new ArrayList<>();
    }
}
