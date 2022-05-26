package com.jupiter.asclepi.core.model.model.entity.disease.consultation;

import com.jupiter.asclepi.core.model.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.VisitId;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Getter
@Setter
@ToString
@Entity(name = "consultation")
@IdClass(ConsultationId.class)
public class Consultation extends AbstractCreationAware<Employee> {

    @Id
    @Column(name = "client_id")
    private BigInteger clientId;

    @Id
    @Column(name = "disease_history_number")
    private Integer diseaseHistoryNumber;

    @Id
    @Column(name = "visit_number")
    private Integer visitNumber;

    @Id
    @GeneratedValue
    @Column(name = "number")
    private Integer number;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "client_id", updatable = false, insertable = false)
    @JoinColumn(name = "disease_history_number", updatable = false, insertable = false)
    @JoinColumn(name = "visit_number", updatable = false, insertable = false)
    private Visit visit;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "anamnesis_id")
    private Anamnesis anamnesis;

    @NotEmpty
    @Column(name = "inspection")
    private String inspection;

    public Consultation(ConsultationId id) {
        setId(id);
    }

    public Consultation() {
    }

    public final void setId(ConsultationId id) {
        clientId = id.getClientId();
        diseaseHistoryNumber = id.getDiseaseHistoryNumber();
        visitNumber = id.getVisitNumber();
        number = id.getNumber();
        this.visit = new Visit(new VisitId(new DiseaseHistoryId(clientId, diseaseHistoryNumber), visitNumber));
    }

    public void setVisit(@NotNull Visit visit) {
        this.visit = visit;
        clientId = visit.getDiseaseHistory().getId().getClient();
        diseaseHistoryNumber = visit.getDiseaseHistory().getNumber();
        visitNumber = visit.getNumber();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Consultation that = (Consultation) o;
        return new EqualsBuilder()
                .append(getClientId(), that.getClientId())
                .append(getDiseaseHistoryNumber(), that.getDiseaseHistoryNumber())
                .append(getVisitNumber(), that.getVisitNumber())
                .append(getNumber(), that.getNumber())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getClientId())
                .append(getDiseaseHistoryNumber())
                .append(getVisitNumber())
                .append(getNumber())
                .toHashCode();
    }

}
