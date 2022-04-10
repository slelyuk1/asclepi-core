package com.jupiter.asclepi.core.model.model.entity.disease.history;

import com.jupiter.asclepi.core.model.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@IdClass(DiseaseHistoryId.class)
public class DiseaseHistory extends AbstractCreationAware<Employee> implements Serializable {

    @Id
    @ManyToOne
    @EqualsAndHashCode.Include
    private Client client;

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

    public DiseaseHistory(DiseaseHistoryId id) {
        this();
        client = new Client(id.getClient());
        number = id.getNumber();
    }
}
