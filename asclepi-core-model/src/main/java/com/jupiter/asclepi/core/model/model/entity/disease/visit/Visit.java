package com.jupiter.asclepi.core.model.model.entity.disease.visit;

import com.jupiter.asclepi.core.model.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity(name = "visit")
@IdClass(VisitId.class)
public class Visit extends AbstractCreationAware<Employee> {

    @Id
    @Column(name = "client_id")
    private BigInteger clientId;

    @Id
    @Column(name = "disease_history_number")
    private Integer diseaseHistoryNumber;

    @Id
    @GeneratedValue
    @Column(name = "number")
    private Integer number;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    @JoinColumn(name = "disease_history_number", insertable = false, updatable = false)
    private DiseaseHistory diseaseHistory;

    @NotNull
    @Column(name = "when")
    private LocalDateTime when;

    public Visit(VisitId id) {
        setId(id);
    }

    public Visit() {
    }

    public final void setId(VisitId id) {
        clientId = id.getClientId();
        diseaseHistoryNumber = id.getDiseaseHistoryNumber();
        number = id.getNumber();
        diseaseHistory = new DiseaseHistory();
        diseaseHistory.setId(new DiseaseHistoryId(clientId, diseaseHistoryNumber));
    }

    public void setDiseaseHistory(DiseaseHistory diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
        clientId = diseaseHistory.getId().getClientId();
        diseaseHistoryNumber = diseaseHistory.getNumber();
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
        return new EqualsBuilder()
                .append(getClientId(), visit.getClientId())
                .append(getDiseaseHistoryNumber(), visit.getDiseaseHistoryNumber())
                .append(getNumber(), visit.getNumber())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getClientId())
                .append(getDiseaseHistoryNumber())
                .append(getNumber())
                .toHashCode();
    }

}
