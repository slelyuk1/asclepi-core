package com.jupiter.asclepi.core.repository.entity.consultation;

import com.jupiter.asclepi.core.repository.entity.Anamnesis;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.repository.helper.api.CreationAware;
import com.jupiter.asclepi.core.repository.helper.api.CreationData;
import com.jupiter.asclepi.core.repository.helper.api.CustomPersistable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Entity(name = "consultation")
@EntityListeners(AuditingEntityListener.class)
public class Consultation implements CustomPersistable<ConsultationId>, CreationAware<String> {

    @EmbeddedId
    private ConsultationId id;

    @ManyToOne
    @MapsId("visitId")
    private Visit visit;

    @ManyToOne(optional = false)
    private Anamnesis anamnesis;

    @Column(nullable = false)
    private String inspection;

    @Embedded
    private CreationData<String> creation;

    public Consultation() {
        id = new ConsultationId();
        creation = new CreationData<>();
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
