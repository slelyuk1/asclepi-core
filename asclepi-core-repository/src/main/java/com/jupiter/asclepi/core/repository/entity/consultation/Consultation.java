package com.jupiter.asclepi.core.repository.entity.consultation;

import com.jupiter.asclepi.core.repository.entity.Anamnesis;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.repository.helper.api.AbstractCreationAware;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Entity(name = "consultation")
public class Consultation extends AbstractCreationAware {

    @EmbeddedId
    private ConsultationId id;

    @ManyToOne
    @MapsId("visitId")
    private Visit visit;

    @NotNull
    @ManyToOne
    private Anamnesis anamnesis;

    @NotEmpty
    private String inspection;

    public Consultation() {
        id = new ConsultationId();
    }

    public void setId(ConsultationId id) {
        this.id = id;
        if (id.getVisitId() != null) {
            setVisit(Visit.fromId(id.getVisitId()));
        }
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
