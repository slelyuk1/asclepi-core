package com.jupiter.asclepi.core.model.model.entity.disease.visit;

import com.jupiter.asclepi.core.model.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "visit")
public class Visit extends AbstractCreationAware<Employee> {

    @EmbeddedId
    private VisitId id;

    @ManyToOne
    @MapsId("diseaseHistory")
    private DiseaseHistory diseaseHistory;

    @NotNull
    @Column(name = "when")
    private LocalDateTime when;

    public void setId(VisitId id) {
        this.id = id;
        if (id.getDiseaseHistory() != null) {
            diseaseHistory = new DiseaseHistory();
            diseaseHistory.setId(id.getDiseaseHistory());
        }
    }

    public void setDiseaseHistory(DiseaseHistory diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
        if (id == null) {
            id = new VisitId();
        }
        id.setDiseaseHistory(diseaseHistory.getId());
    }

    @Deprecated
    public void setNumber(int number) {
        if (id == null) {
            id = new VisitId();
        }
        id.setNumber(number);
    }

    @Deprecated
    public Integer getNumber() {
        if (id == null) {
            return null;
        }
        return id.getNumber();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Visit visit = (Visit) o;
        return new EqualsBuilder().append(getId(), visit.getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
    }

}
