package com.jupiter.asclepi.core.repository.entity;

import com.jupiter.asclepi.core.repository.entity.id.DiseaseHistoryId;
import com.jupiter.asclepi.core.repository.helper.api.AbstractCreationAware;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class DiseaseHistory extends AbstractCreationAware<Employee> {

    @EmbeddedId
    private DiseaseHistoryId id;

    @ManyToOne
    @MapsId("clientId")
    private Client client;

    @ManyToOne(optional = false)
    private Employee doctor;

    @OneToMany
    private List<Diagnosis> diagnoses;

    public static DiseaseHistory fromId(DiseaseHistoryId id) {
        DiseaseHistory toReturn = new DiseaseHistory();
        toReturn.setId(id);
        return toReturn;
    }

    public DiseaseHistory() {
        id = new DiseaseHistoryId();
        diagnoses = new ArrayList<>();
    }

    public void setId(DiseaseHistoryId id) {
        this.id = id;
        if (id.getClientId() != null) {
            setClient(Client.fromId(id.getClientId()));
        }
    }

    public void setClient(Client client) {
        this.client = client;
        getId().setClientId(client.getId());
    }

    @Deprecated
    public Integer getNumber() {
        return id.getNumber();
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

}
