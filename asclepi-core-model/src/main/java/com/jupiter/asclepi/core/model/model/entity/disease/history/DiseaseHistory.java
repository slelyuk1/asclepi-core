package com.jupiter.asclepi.core.model.model.entity.disease.history;

import com.jupiter.asclepi.core.model.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "disease_history")
@IdClass(DiseaseHistoryId.class)
public class DiseaseHistory extends AbstractCreationAware<Employee> {

    @Id
    @Column(name = "client_id")
    private BigInteger clientId;

    @Id
    @GeneratedValue
    @Column(name = "number")
    private Integer number;

    @ManyToOne
    @MapsId("clientId")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "doctor_id")
    private Employee doctor;

    @OneToMany
    private List<Diagnosis> diagnoses;

    public DiseaseHistory() {
        diagnoses = new ArrayList<>();
    }

    public DiseaseHistoryId getId() {
        return new DiseaseHistoryId(getClient().getId(), getNumber());
    }

    public void setId(DiseaseHistoryId id) {
        client = new Client();
        client.setId(id.getClientId());
        number = id.getNumber();
    }

    // todo add javadoc
    @Deprecated
    public Integer getNumber() {
        return number;
    }

    protected void setDiagnoses(List<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
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
        return new EqualsBuilder().append(getId(), that.getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", getId())
                .append("client", getClient())
                .append("doctor", getDoctor())
                .toString();
    }

}
