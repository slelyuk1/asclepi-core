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
@IdClass(VisitId.class)
public class Visit extends AbstractCreationAware<Employee> {

    @Id
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JoinColumn(name = "disease_history_number")
    private DiseaseHistory diseaseHistory;

    @Id
    @Column(name = "number")
    private Integer number;

    @NotNull
    @Column(name = "when")
    private LocalDateTime when;

    public VisitId getId() {
        DiseaseHistory diseaseHistoryToExtractId = getDiseaseHistory();
        return new VisitId(
                diseaseHistoryToExtractId != null ? diseaseHistoryToExtractId.getId() : null,
                getNumber()
        );
    }

    public void setId(VisitId id) {
        DiseaseHistory diseaseHistoryToSet = new DiseaseHistory();
        diseaseHistoryToSet.setId(id.getDiseaseHistory());
        setDiseaseHistory(diseaseHistoryToSet);
        setNumber(id.getNumber());
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
