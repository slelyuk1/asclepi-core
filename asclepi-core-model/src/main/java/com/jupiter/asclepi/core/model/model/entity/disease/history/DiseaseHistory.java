package com.jupiter.asclepi.core.model.model.entity.disease.history;

import com.jupiter.asclepi.core.model.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity(name = "disease_history")
@IdClass(DiseaseHistoryId.class)
public class DiseaseHistory extends AbstractCreationAware<Employee> implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Id
    @GeneratedValue
    @Column(name = "number")
    private Integer number;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Employee doctor;


    @Setter(AccessLevel.PRIVATE)
    @NotNull
    @OneToMany
    private List<Diagnosis> diagnoses;

    public DiseaseHistory(DiseaseHistoryId id) {
        this();
        client = new Client(id.getClient());
        number = id.getNumber();
    }

    public DiseaseHistory() {
        diagnoses = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DiseaseHistory that = (DiseaseHistory) o;
        return new EqualsBuilder()
                .append(getClient(), that.getClient())
                .append(getNumber(), that.getNumber())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getClient())
                .append(getNumber())
                .toHashCode();
    }

}
