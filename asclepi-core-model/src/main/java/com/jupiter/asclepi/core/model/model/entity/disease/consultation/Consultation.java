package com.jupiter.asclepi.core.model.model.entity.disease.consultation;

import com.jupiter.asclepi.core.model.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Entity(name = "consultation")
public class Consultation extends AbstractCreationAware<Employee> {

    @EmbeddedId
    private ConsultationId id;

    @ManyToOne
    @MapsId("visitId")
    private Visit visit;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "anamnesis_id")
    private Anamnesis anamnesis;

    @NotEmpty
    @Column(name = "inspection")
    private String inspection;

    public static Consultation fromId(ConsultationId id) {
        Consultation toReturn = new Consultation();
        toReturn.setId(id);
        return toReturn;
    }

    public Consultation() {
        id = new ConsultationId();
    }

    public void setId(ConsultationId id) {
        this.id = id;
        if (id.getVisitId() != null) {
            setVisit(Visit.fromId(id.getVisitId()));
        }
    }

    @Deprecated
    public Integer getNumber() {
        if (id == null) {
            return null;
        }
        return id.getNumber();
    }

    public void setVisit(@NotNull Visit visit) {
        this.visit = visit;
        if (id == null) {
            id = new ConsultationId();
        }
        id.setVisitId(visit.getId());
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
        return new EqualsBuilder().append(getId(), that.getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
    }

}
